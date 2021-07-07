package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class GodBoxService {

    private final GodBoxFileService godBoxFileService;

    private static final String url = "https://api.godbox.qtmsheep.com/run";

    public GodBoxResponse runWithCompilation(String username, String code) {
        var directoryPath = prepareUserFolder(username);
        var fileName = directoryPath + "/main.c";
        createFileFromUserCode(fileName, code);

        try {
            var codeEncoded = getEncodedCode(directoryPath.toString());
            var godBoxBody = new GodBoxBody(username, codeEncoded);

            var response = sendRequest(godBoxBody);

            if (response.isEmpty()) {
                throw new RuntimeException("An error occurred while communicating with external service.");
            }

            godBoxFileService.deleteFile(fileName);
            return response.get();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occurred while zipping folder");
        }
    }

    private Path prepareUserFolder(String username) {
        return godBoxFileService.createDirectory(username);
    }

    private void createFileFromUserCode(String fileName, String code) {
        godBoxFileService.createFileWithContent(fileName, code);
    }

    private String getEncodedCode(String directory) throws IOException {
        return godBoxFileService.zipBase64(directory);
    }

    private Optional<GodBoxResponse> sendRequest(GodBoxBody body) {
        try {
            var objMapper = new ObjectMapper();
            var json = objMapper.writeValueAsString(body);

            var jsonResponse = Unirest.post(url)
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .body(json).asJsonAsync().get();

            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return Optional.empty();
            }

            var jsonBody = jsonResponse.getBody().toString();
            var response = objMapper.readValue(jsonBody, GodBoxResponse.class);

            return Optional.of(response);

        } catch (IOException | InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }
}
