package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponGame;
import lombok.Data;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Data
@Service
public class GodBoxFileService {

    private final String root;
    private final String resources;
    private final String scripts;
    private final ObjectMapper mapper;

    @Autowired
    public GodBoxFileService() {
        root = "game-engine";
        resources = root + "/resources";
        scripts = root + "/src/scripts";
        mapper = new ObjectMapper();
    }

    public Path createDirectory(String name) {
        var directory = new File(root + name);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        return directory.toPath();
    }

    public void createWarriorFileWithContent(String fileName, List<Warrior> content) {
        try {
            createDirectory(this.resources);

            var writer = new FileWriter(fileName);
            writer.write(mapper.writeValueAsString(content));
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createWeaponFileWithContent(String fileName, List<WeaponGame> content) {
        try {
            var writer = new FileWriter(fileName);
            writer.write(mapper.writeValueAsString(content));
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createFileWithContent(String fileName, String content) {
        try {
            var writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteResourcesAndScripts() {
        var filePaths = new ArrayList<String>();

        filePaths.add(this.resources + "/warriors.json");
        filePaths.add(this.resources + "/weapons1.json");
        filePaths.add(this.resources + "/weapons2.json");
        filePaths.add(this.scripts + "/user1.c");
        filePaths.add(this.scripts + "/user2.c");

        filePaths.forEach(this::deleteFile);
    }

    private boolean deleteFile(String fileName) {
        var file = new File(fileName);
        var isDeleted = false;

        if (file.exists()) {
            isDeleted = file.delete();
        }

        return isDeleted;
    }

    public String zipBase64() throws IOException {
        var zipFileName = this.root + ".zip";
        new ZipFile(zipFileName).addFolder(new File(this.root));

        var zipFile = new File(zipFileName);

        byte[] bytes = Files.readAllBytes(zipFile.toPath());
        return Base64.getEncoder().encodeToString(bytes);
    }
}
