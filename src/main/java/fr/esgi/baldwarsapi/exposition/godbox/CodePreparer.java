package fr.esgi.baldwarsapi.exposition.godbox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@RequiredArgsConstructor
public class CodePreparer {

    public String prepare(String fileName, String code) {
        try {
            var files = new ArrayList<File>();
            var codeFile = createCodeFile(fileName, code);

            files.add(codeFile);
            var zipBase64 = zipBase64(files);

            if (!codeFile.delete()) {
                throw new RuntimeException("Failed to delete the file.");
            }

            return zipBase64;

        } catch (IOException exception) {
            return "";
        }
    }

    private File createCodeFile(String fileName, String code) {
        try {
            var writer = new FileWriter(fileName);
            writer.write(code);
            writer.close();

            var file = new File(fileName);

            if (file.exists()) {
                return file;
            } else {
                throw new FileNotFoundException("Error: File with path: " + file.getAbsolutePath() + "does not exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred.");
        }
    }

    private String zipBase64(List<File> files) throws IOException {
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
