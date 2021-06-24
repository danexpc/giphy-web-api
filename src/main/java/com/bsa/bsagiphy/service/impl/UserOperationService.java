package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.dto.GenerateGifForUserDto;
import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.entity.UserHistory;
import com.bsa.bsagiphy.repository.impl.CacheMemoryRepository;
import com.bsa.bsagiphy.repository.impl.DiskStorageRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserOperationService {

    @Autowired
    private DiskStorageRepository storageRepository;

    @Autowired
    private CacheMemoryRepository memoryRepository;

    @Autowired
    private HttpGifsApiClient apiClient;

    public List<Cache> getAllPersonalFilesByUserId(String userId) {
        return storageRepository.getCacheByUserId(userId);
    }

    public List<UserHistory> getHistoryByUserId(String userId) {
        return storageRepository.getHistoryByUserId(userId);
    }

    public void cleanHistoryByUserId(String userId) {
        storageRepository.deleteHistoryByUserId(userId);
    }

    public Gif searchFileInDiskStorageByQuery(String userId, String query) {
        var maybeGif = storageRepository.getFileByQuery(userId, query);
        maybeGif.ifPresent(gif -> memoryRepository.updateCache(userId, query, gif));

        if (maybeGif.isPresent())
            return maybeGif.get();

        throw new NoSuchElementException();
    }

    public Gif searchGifInCacheMemoryByQuery(String userId, String query) {
        var maybeGif = memoryRepository.getFileByQuery(userId, query);

        return maybeGif.orElseGet(() -> searchFileInDiskStorageByQuery(userId, query));
    }

    @SneakyThrows
    public String createGif(String userId, GenerateGifForUserDto dto) {
        if (dto.getForce()) {
            var gif = apiClient.getGif(dto.getQuery());
            memoryRepository.updateCache(userId, dto.getQuery(), gif);
            return gif.getPath();
        }

        var maybeGif = storageRepository.getFileFromStorage(dto.getQuery());
        Gif gif;
        if (maybeGif.isPresent()) {
            gif = storageRepository.saveFileToUserStorage(userId, dto.getQuery(), maybeGif.get());
        } else {
            gif = apiClient.getGif(dto.getQuery());
            gif = storageRepository.saveFileToUserStorage(userId, dto.getQuery(), gif);
        }
        memoryRepository.updateCache(userId, dto.getQuery(), gif);
        return gif.getPath();
    }

    public void resetUserCacheByQuery(String userId, String query) {
        memoryRepository.deleteCacheByQuery(userId, query);
    }

    public void resetAllUserCache(String userId) {
        memoryRepository.deleteCache(userId);
    }

    public void cleanAllUserData(String userId) {
        memoryRepository.deleteCache(userId);
        storageRepository.deleteUserStorage(userId);
    }
}
