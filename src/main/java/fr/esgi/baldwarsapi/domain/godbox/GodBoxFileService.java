package fr.esgi.baldwarsapi.domain.godbox;

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

    private final String mainDirectoryPath;

    @Autowired
    public GodBoxFileService() {
        mainDirectoryPath = "godbox/";
    }

    public Path createDirectory(String name) {
        var directory = new File(mainDirectoryPath + name);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        return directory.toPath();
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

    public boolean deleteFile(String fileName) {
        var file = new File(fileName);
        var isDeleted = false;

        if (file.exists()) {
            isDeleted = file.delete();
        }

        return isDeleted;
    }

    public String zipBase64(String folder) throws IOException {
//        var zipFileName = folder + ".zip";
        var zipFileName = "code.zip";
//        new ZipFile(zipFileName).addFolder(new File(folder));
        new ZipFile(zipFileName).addFile(folder);

        var zipFile = new File(zipFileName);

        byte[] bytes = Files.readAllBytes(zipFile.toPath());
        return Base64.getEncoder().encodeToString(bytes);
    }
}
