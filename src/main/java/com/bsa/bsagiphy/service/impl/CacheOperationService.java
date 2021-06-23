package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.dto.GenerateCacheRequestDto;
import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.repository.impl.DiskStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CacheOperationService {

    private final DiskStorageRepository repository;
    private final GifOperationService service;

    @Autowired
    public CacheOperationService(DiskStorageRepository repository, GifOperationService service) {
        this.repository = repository;
        this.service = service;
    }

    public Optional<Cache> getCacheByQuery(String query) {
        return repository.getCacheByQuery(query);
    }

    public List<Cache> getAllCache() {
        return repository.getCache();
    }

    public Optional<Cache> createGifInCache(GenerateCacheRequestDto dto) {
        service.get(dto.query);
        return getCacheByQuery(dto.query);
    }

    public void deleteCache() {
        // todo
    }
}
