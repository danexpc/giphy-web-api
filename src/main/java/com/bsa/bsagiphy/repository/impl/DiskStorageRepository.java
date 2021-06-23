package com.bsa.bsagiphy.repository.impl;

import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.repository.GifRepository;
import com.bsa.bsagiphy.util.OperationsWithFileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.bsa.bsagiphy.util.OperationsWithFileSystem.*;

@Repository
public class DiskStorageRepository implements GifRepository {

    @Value("${resources.location.cache}")
    private String pathToCache;

    @Value("${resources.location.users}")
    private String pathToUsersStorage;

    @Value("${resources.location.users.history-file-name}")
    private String historyFileName;

    @Value("${resources.file-extension}")
    private String fileExtension;

    public List<Cache> getCache() {
        var file = new File(pathToCache);

        return getCacheFromDir(file);
    }

    public Optional<Cache> getCacheByQuery(String query) {
        var file = new File(pathToCache);

        var maybeDir = Arrays.stream(Objects.requireNonNull(file.listFiles())).filter(
                dir -> dir.getName().equals(query)).findFirst();
        if (maybeDir.isPresent()) {
            var paths = Arrays.stream(
                    Objects.requireNonNull(maybeDir.get().listFiles())).map(File::getPath).collect(Collectors.toList());
            return Optional.of(new Cache(maybeDir.get().getName(), paths));
        }

        return Optional.empty();
    }

    public void deleteCache() {
        deleteAllContentInDir(new File(pathToCache));
    }

    public List<Gif> getFiles() {
        var files = getAllFilesInDir(new File(pathToCache));

        return files.stream().map(file ->
                new Gif(file.getName().replace(fileExtension, ""),
                        file.getName().replace(fileExtension, ""),
                        file.getPath())).collect(Collectors.toList());
    }

    public List<Cache> getCacheByUserId(String userId) {

        var dir = new File(pathToUsersStorage + userId);

        return getCacheFromDir(dir);
    }

    public Optional<Gif> getFileByQuery(String userId, String query) {
        var sourceDir = Path.of(pathToCache + query);
        Optional<File> maybeFile = getRandomFileFromDirectory(sourceDir);
        maybeFile.ifPresent(file -> OperationsWithFileSystem.copyFile(file,
                new File(pathToUsersStorage + userId + File.separator + file.getName())));

        if (maybeFile.isPresent()) {
            var file = maybeFile.get();
            return Optional.of(new Gif(file.getName(), file.getName(), file.getPath()));
        }

        return Optional.empty();
    }

    public void updateHistory(String userId, String query, Gif gif) throws IOException {
        try (var fileWriter = new FileWriter(pathToUsersStorage + userId + File.separator + historyFileName);
             var printWriter = new PrintWriter(fileWriter)) {
            printWriter.write(buildNewHistoryRecord(query, gif));
        }
    }

    private String buildNewHistoryRecord(String query, Gif gif) {
        var dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String todayDate = LocalDate.now().format(dtf);
        return todayDate + "," + query + "," + gif.getPath();
    }

    private List<Cache> getCacheFromDir(File dir) {
        List<Cache> cache = new ArrayList<>();
        var dirs = dir.listFiles();

        if (dirs != null) {
            for (var d : dirs) {
                var paths = Arrays.stream(Objects.requireNonNull(d.listFiles())).map(File::getPath).collect(Collectors.toList());
                cache.add(new Cache(d.getName(), paths));
            }
        }

        return cache;
    }

}
