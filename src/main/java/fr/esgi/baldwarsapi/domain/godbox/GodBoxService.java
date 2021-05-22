package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GodBoxService {

    private final GodBoxFileService godBoxFileService;

    private static final String url = "http://godbox:8080/run";

    public GodBoxResponse runWithCompilation(String username, String code) {
        var directoryPath = prepareUserFolder(username);
        var fileName = directoryPath + "/main.c";
        createFileFromUserCode(fileName, code);

        var codeEncoded = getEncodedCode(directoryPath.toString());
        var godBoxBody = new GodBoxBody(codeEncoded);

        var response = sendRequest(godBoxBody);

        if (response.isEmpty()) {
            throw new RuntimeException("An error occurred while communicating with external service.");
        }

        godBoxFileService.deleteFile(fileName);

        return response.get();
    }

    private Path prepareUserFolder(String username) {
        return godBoxFileService.createDirectory(username);
    }

    private void createFileFromUserCode(String fileName, String code) {
        godBoxFileService.createFileWithContent(fileName, code);
    }

    private String getEncodedCode(String directory) {
        var optionalFiles = godBoxFileService.getFilesListFromDirectory(directory);
        
        if (optionalFiles.isEmpty()) return "";
        
        try {
            return godBoxFileService.zipBase64(optionalFiles.get());
        } catch (IOException e) {
            return "";
        }
    }

    private Optional<GodBoxResponse> sendRequest(GodBoxBody body) {
        try {
            var objMapper = new ObjectMapper();
            var json = objMapper.writeValueAsString(body);
            System.out.println("json: " + json);
            var jsonResponse = Unirest.post(url).body(json).asJson();
            System.out.println("json response status:");
            System.out.println(jsonResponse.getStatus());

            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return Optional.empty();
            }

            var jsonBody = jsonResponse.getBody().toString();
            var response = objMapper.readValue(jsonBody, GodBoxResponse.class);

            return Optional.of(response);

        } catch (UnirestException | IOException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
