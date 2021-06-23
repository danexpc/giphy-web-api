package com.bsa.bsagiphy.repository.impl;

import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.entity.UserHistory;
import com.bsa.bsagiphy.repository.GifRepository;
import com.bsa.bsagiphy.util.OperationsWithFileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
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

    @Value("${resources.location.users.history.filename}")
    private String historyFileName;

    @Value("${resources.location.users.history.separator}")
    private String historyRecordSeparator;

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
        var sourceDir = Path.of(pathToUsersStorage + userId + File.separator + query);
        Optional<File> maybeFile = getRandomFileFromDirectory(sourceDir);
        if (maybeFile.isPresent()) {
            var file = maybeFile.get();
            return Optional.of(new Gif(file.getName(), file.getName(), file.getPath()));
        }

        return Optional.empty();
    }

    public List<UserHistory> getHistoryByUserId(String userId) {
        List<UserHistory> histories = new ArrayList<>();
        try (var reader = new Scanner(new File(pathToUsersStorage + userId + File.separator + historyFileName))) {
            while (reader.hasNextLine()) {
                histories.add(parseHistoryRecord(reader.nextLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return histories;
    }

    public void updateHistory(String userId, String query, Gif gif) throws IOException {
        try (var fileWriter = new FileWriter(pathToUsersStorage + userId + File.separator + historyFileName);
             var printWriter = new PrintWriter(fileWriter)) {
            printWriter.write(buildNewHistoryRecord(query, gif));
        }
    }

    public void deleteHistoryByUserId(String userId) {
        deleteFileContent(pathToUsersStorage + userId + File.separator + historyFileName);
    }

    private String buildNewHistoryRecord(String query, Gif gif) {
        var dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String todayDate = LocalDate.now().format(dtf);
        return todayDate + historyRecordSeparator + query + historyRecordSeparator + gif.getPath();
    }

    private UserHistory parseHistoryRecord(String historyRecord) {
        var fields = historyRecord.split(historyRecordSeparator);
        var formatter = new SimpleDateFormat("MM-dd-yyyy");
        var date = new Date();

        try {
            date = formatter.parse(fields[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new UserHistory(date, fields[1], fields[2]);
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
