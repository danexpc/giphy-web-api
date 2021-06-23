package com.bsa.bsagiphy.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class OperationsWithFileSystem {

    private static final Random random = new Random();

    private OperationsWithFileSystem() {}

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

}
