package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import fr.esgi.baldwarsapi.domain.godbox.models.GodBoxBody;
import fr.esgi.baldwarsapi.domain.godbox.models.GodBoxResponse;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponGame;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class GodBoxService {

    private final GodBoxFileService godBoxFileService;

    private static final String url = "https://api.godbox.qtmsheep.com/run";

    public GodBoxResponse runWithCompilation() {
        try {
            var codeEncoded = getEncodedCode();
            var godBoxBody = new GodBoxBody(codeEncoded);

            var response = sendRequest(godBoxBody);

            if (response.isEmpty()) {
                throw new RuntimeException("An error occurred while communicating with external service.");
            }

            godBoxFileService.deleteResourcesAndScripts();
            return response.get();

        } catch (IOException e) {
            throw new RuntimeException("An error occurred while zipping folder");
        }
    }

    private String getEncodedCode() throws IOException {
        return godBoxFileService.zipBase64();
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
                System.out.println("response status: " + jsonResponse.getStatus());
                System.out.println("response body: " + jsonResponse.getBody().toString());
                return Optional.empty();
            }

            var jsonBody = jsonResponse.getBody().toString();
            var response = objMapper.readValue(jsonBody, GodBoxResponse.class);

            return Optional.of(response);

        } catch (IOException | InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }

    public void prepareResourceFiles(List<Warrior>warriors, List<List<WeaponGame>> weapons) {
        var warriorFileName = this.godBoxFileService.getResources() + "/warriors.json";
        var weapon1FileName = this.godBoxFileService.getResources() + "/weapons1.json";
        var weapon2FileName = this.godBoxFileService.getResources() + "/weapons2.json";

        this.godBoxFileService.createWarriorFileWithContent(warriorFileName, warriors);
        this.godBoxFileService.createWeaponFileWithContent(weapon1FileName, weapons.get(0));
        this.godBoxFileService.createWeaponFileWithContent(weapon2FileName, weapons.get(1));
    }

    public void prepareScriptFiles(Script a, Script b) {
        var player1 = this.godBoxFileService.getScripts() + "/user1.c";
        var player2 = this.godBoxFileService.getScripts() + "/user2.c";

        var content1 = this.wrapUserScript(a, 1);
        var content2 = this.wrapUserScript(b, 2);

        this.godBoxFileService.createFileWithContent(player1, content1);
        this.godBoxFileService.createFileWithContent(player2, content2);
    }

    private String wrapUserScript(Script script, Integer playerNumber) {
        var startWrapping = "#include \"scripts.h\"\n" +
                "\n" +
                "void run_script_user" + playerNumber + "() {\n";
        var endWrapping = "\n}";

        return startWrapping + script.getContent() + endWrapping;
    }
}
