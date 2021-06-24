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

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserOperationService {

    @Autowired
    private DiskStorageRepository storageRepository;

    @Autowired
    private CacheMemoryRepository memoryRepository;

    @Autowired
    private HttpGifsApiClient apiClient;

    public List<Cache> getAllGifsById(String userId) {
        return storageRepository.getCacheByUserId(userId);
    }

    public List<UserHistory> getHistoryById(String userId) {
        return storageRepository.getHistoryByUserId(userId);
    }

    public void deleteHistoryById(String userId) {
        storageRepository.deleteHistoryByUserId(userId);
    }

    public Gif getGifInStorageByQuery(String userId, String query) {
        var maybeGif = storageRepository.getGifByQuery(userId, query);
        maybeGif.ifPresent(gif -> memoryRepository.updateCache(userId, query, gif));

        if (maybeGif.isPresent())
            return maybeGif.get();

        throw new NoSuchElementException();
    }

    public Gif getGifInCacheByQuery(String userId, String query) {
        var maybeGif = memoryRepository.getGifByQuery(userId, query);

        return maybeGif.orElseGet(() -> getGifInStorageByQuery(userId, query));
    }

    @SneakyThrows
    public String createGif(String userId, GenerateGifForUserDto dto) {
        if (dto.getForce()) {
            var gif = apiClient.getGif(dto.getQuery());

            memoryRepository.updateCache(userId, dto.getQuery(), gif);
            storageRepository.updateHistoryByUserId(userId, dto.getQuery(), gif);
            return gif.getPath();
        }

        var maybeGif = storageRepository.getGifFromStorageByQuery(dto.getQuery());
        Gif gif;
        if (maybeGif.isPresent()) {
            gif = storageRepository.saveGifToUserStorage(userId, dto.getQuery(), maybeGif.get());
        } else {
            gif = apiClient.getGif(dto.getQuery());
            gif = storageRepository.saveGifToUserStorage(userId, dto.getQuery(), gif);
        }
        memoryRepository.updateCache(userId, dto.getQuery(), gif);
        storageRepository.updateHistoryByUserId(userId, dto.getQuery(), gif);
        return gif.getPath();
    }

    public void resetCacheByQuery(String userId, String query) {
        memoryRepository.deleteCacheByQuery(userId, query);
    }

    public void resetAllCache(String userId) {
        memoryRepository.deleteCache(userId);
    }

    public void deleteAllData(String userId) {
        memoryRepository.deleteCache(userId);
        storageRepository.deleteUserStorage(userId);
    }
}
