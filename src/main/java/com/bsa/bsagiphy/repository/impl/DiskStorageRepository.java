package com.bsa.bsagiphy.repository.impl;

import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.entity.UserHistory;
import com.bsa.bsagiphy.repository.GifRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
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

    private static final String fileExtension = ".gif";

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

    public List<Cache> getCacheByUserId(String userId) {

        var dir = new File(pathToUsersStorage + userId);

        return getCacheFromDir(dir);
    }

    public void deleteCache() {
        deleteAllContentInDir(new File(pathToCache));
    }

    public List<Gif> getGifs() {
        var files = getAllFilesInDir(new File(pathToCache));

        return files.stream().map(file ->
                new Gif(file.getName().replace(fileExtension, ""),
                        file.getName().replace(fileExtension, ""),
                        file.getPath())).collect(Collectors.toList());
    }

    public Optional<Gif> getGifByQuery(String userId, String query) {
        var sourceDir = Path.of(pathToUsersStorage + userId + File.separator + query);
        Optional<File> maybeFile = getRandomFileFromDirectory(sourceDir);
        if (maybeFile.isPresent()) {
            var file = maybeFile.get();
            return Optional.of(new Gif(file.getName(), file.getName(), file.getPath()));
        }
        return Optional.empty();
    }

    public Optional<Gif> getGifFromStorageByQuery(String query) {
        var file = getRandomFileFromDirectory(Path.of(pathToCache + query));
        return file.map(value -> new Gif(
                value.getName().replace(fileExtension, ""),
                value.getName().replace(fileExtension, ""),
                value.getPath()));
    }

    public Gif saveGifToUserStorage(String userId, String query, Gif gif) throws IOException {
        var newPath = Path.of(pathToUsersStorage, userId, query, gif.getId() + fileExtension).toString();
        log.error(newPath);
        FileUtils.copyFile(new File(gif.getPath()), new File(newPath));
        return new Gif(gif.getId(), gif.getName(), newPath);
    }

    public List<UserHistory> getHistoryByUserId(String userId) {
        List<UserHistory> histories = new ArrayList<>();
        try {
            FileUtils.readLines(Paths.get(pathToUsersStorage, userId, historyFileName).toFile(), Charset.defaultCharset())
                    .forEach(rec -> histories.add(parseHistoryRecord(rec)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return histories;
    }

    public void updateHistoryByUserId(String userId, String query, Gif gif) throws IOException {
        FileUtils.writeStringToFile(
                Paths.get(pathToUsersStorage, userId, historyFileName).toFile(),
                buildNewHistoryRecord(query, gif), Charset.defaultCharset(), true);
    }

    public void deleteHistoryByUserId(String userId) {
        try {
            deleteFileContent(pathToUsersStorage + userId + File.separator + historyFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserStorage(String userId) {
        try {
            FileUtils.deleteDirectory(Paths.get(pathToUsersStorage, userId).toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildNewHistoryRecord(String query, Gif gif) {
        var dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String todayDate = LocalDate.now().format(dtf);
        return todayDate + historyRecordSeparator + query + historyRecordSeparator + gif.getPath() + "\n";
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
                if (d.isDirectory()) {
                    var paths = Arrays.stream(Objects.requireNonNull(d.listFiles()))
                            .map(File::getPath).collect(Collectors.toList());

                    cache.add(new Cache(d.getName(), paths));
                }
            }
        }
        return cache;
    }

    private void deleteFileContent(String filename) throws FileNotFoundException {
        new PrintWriter(filename).close();
    }

    private List<File> getAllFilesInDir(File dir) {
        var dirs = dir.listFiles();
        var files = new ArrayList<File>();

        if (dirs != null) {
            for (var d : dirs) {
                files.addAll(Arrays.stream(Objects.requireNonNull(d.listFiles())).collect(Collectors.toList()));
            }
        }

        return files;
    }

    private void deleteAllContentInDir(File dir) {
        var dirs = dir.listFiles();
        if (dirs != null) {
            for (File file : dirs) {
                try {
                    FileUtils.delete(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Optional<File> getRandomFileFromDirectory(Path path) {
        if (Files.isDirectory(path)) {
            File[] files = path.toFile().listFiles();
            if (files != null) {
                return Optional.of(files[new Random().nextInt(files.length)]);
            }
        }
        return Optional.empty();
    }
}
