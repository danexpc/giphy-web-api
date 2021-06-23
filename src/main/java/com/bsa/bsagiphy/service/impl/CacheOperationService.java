package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.repository.GifRepository;
import com.bsa.bsagiphy.repository.impl.DiskStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CacheOperationService {

    private final DiskStorageRepository repository;

    @Autowired
    public CacheOperationService(DiskStorageRepository repository) {
        this.repository = repository;
    }

    public Optional<Cache> getCacheByQuery(String query) {
        return repository.getCacheByQuery(query);
    }

    public List<Cache> getAllCache() {
        return repository.getCache();
    }

    public Cache createGifInCache() {
        // todo
        return null;
    }

    public void deleteCache() {
        // todo
    }
}
