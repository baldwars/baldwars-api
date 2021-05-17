package fr.esgi.baldwarsapi.exposition.godbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GodBoxService {

    private final CodePreparer codePreparer;

    private static final String url = "http://godbox:8080/run";

    public GodBoxResponse runWithCompilation(String code) {
        var encodedFiles = codePreparer.prepare("main.c", code);
        var godBoxBody = new GodBoxBody(encodedFiles);

        var response = sendRequest(godBoxBody);

        if (response.isEmpty()) {
            throw new RuntimeException("An error occurred while communicating with external service.");
        }

        return response.get();

    }

    private Optional<GodBoxResponse> sendRequest(GodBoxBody body) {
        try {
            var objMapper = new ObjectMapper();
            var json = objMapper.writeValueAsString(body);
            var jsonResponse = Unirest.post(url).body(json).asJson();

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
