package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.dto.GenerateGifForUserDto;
import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.entity.UserHistory;
import com.bsa.bsagiphy.repository.impl.CacheMemoryRepository;
import com.bsa.bsagiphy.repository.impl.DiskStorageRepository;
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

    public List<Cache> getAllPersonalFilesByUserId(String userId) {
        return storageRepository.getCacheByUserId(userId);
    }

    public List<UserHistory> getHistoryByUserId(String userId) {
        return storageRepository.getHistoryByUserId(userId);
    }

    public void cleanHistoryByUserId(String userId) {
        storageRepository.deleteHistoryByUserId(userId);
    }

    public Gif searchGifInDiskStorageByQuery(String userId, String query) {
        var maybeGif = storageRepository.getFileByQuery(userId, query);
        maybeGif.ifPresent(gif -> memoryRepository.updateCache(userId, query, gif));

        if (maybeGif.isPresent())
            return maybeGif.get();

        throw new NoSuchElementException();
    }

    public Gif searchGifInCacheMemoryByQuery(String userId, String query) {
        var maybeGif = memoryRepository.getFileByQuery(userId, query);

        return maybeGif.orElseGet(() -> searchGifInDiskStorageByQuery(userId, query));
    }

    public Gif createGif(String id, GenerateGifForUserDto dto) {
        // todo
        return null;
    }

    public void resetUserCacheByQuery(String id, String query) {
        // todo
    }

    public void resetAllUserCache(String id) {
        // todo
    }

    public void cleanAllUserData(String id) {
        // todo
    }
}
