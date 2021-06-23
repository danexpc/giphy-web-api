package com.bsa.bsagiphy.util;

import com.bsa.bsagiphy.entity.Cache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.catalina.startup.ExpandWar.deleteDir;

public class OperationsWithFileSystem {

    private static final Random random = new Random();

    private OperationsWithFileSystem() {
    }

    public static void copyFile(File source, File destination) {
        try (InputStream in = destination.toURI().toURL().openStream()) {
            Files.copy(in, source.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<File> getRandomFileFromDirectory(Path path) {
        if (Files.isDirectory(path)) {
            File[] files = path.toFile().listFiles();
            if (files != null) {
                return Optional.of(files[random.nextInt(files.length)]);
            }
        }
        return Optional.empty();
    }

    public static boolean isDirectoryEmpty(Path path) {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                return entries.findFirst().isEmpty();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;

    }

    public static void deleteAllContentInDir(File dir) {
        var contents = dir.listFiles();
        for (File content : Objects.requireNonNull(contents)) {
            try {
                deleteDirRecursively(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteDirRecursively(File file) throws IOException {
        var contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    deleteDirRecursively(f);
                }
            }
        }
        Files.delete(file.toPath());
    }

    public static List<File> getAllFilesInDir(File dir) {
        var dirs = dir.listFiles();
        var files = new ArrayList<File>();

        if (dirs != null) {
            for (var d : dirs) {
                files.addAll(Arrays.stream(Objects.requireNonNull(d.listFiles())).collect(Collectors.toList()));
            }
        }

        return files;
    }

    public static void deleteFileContent(String filePath) {
        try {
            new PrintWriter(filePath).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
