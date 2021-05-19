package fr.esgi.baldwarsapi.exposition.godbox;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Data
@Service
public class GodBoxFileService {

    private final String mainDirectoryPath;

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

    public Optional<List<File>> getFilesListFromDirectory(String directoryName) {
        var directory = new File(directoryName);
        var files = new ArrayList<File>();

        if (!directory.exists() || !directory.isDirectory()) return Optional.empty();

        var list = directory.listFiles();

        if (list == null) return Optional.empty();

        for (var file : list) {
            if (file.isFile()) {
                files.add(file);
            }
        }

        return Optional.of(files);
    }

    public String zipBase64(List<File> files) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (File f : files) {
                try (FileInputStream fis = new FileInputStream(f)) {
                    zos.putNextEntry(new ZipEntry(f.getName()));
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
