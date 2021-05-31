package sront.crawler;


import com.fasterxml.jackson.databind.ObjectMapper;
import sront.crawler.schema.input.Sites;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    public static Sites GetInputSites(String inputFilename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String inputJsonContents =
                new String(Files.readAllBytes(Paths.get(inputFilename)));
        return mapper.readValue(inputJsonContents,
                Sites.class);
    }

    public static void CreateOutputDirsAndFile(String outputFilename) throws IOException {
        File outputFile = Paths.get(outputFilename).toFile();
        final boolean createdDirs = outputFile.getParentFile().mkdirs();
        if (!createdDirs) {
            System.out.println("Couldn't create dirs for: " + outputFile.getParentFile().toPath());
        }
        Files.deleteIfExists(outputFile.toPath());
        final boolean createdFile = outputFile.createNewFile();
        if (!createdFile) {
            throw new IOException("Couldn't create file: " + outputFilename);
        }
    }
}
