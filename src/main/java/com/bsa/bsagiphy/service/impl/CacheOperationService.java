package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.dto.GenerateCacheRequestDto;
import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.repository.DiskStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheOperationService {

    private final DiskStorageRepository repository;

    @Autowired
    private HttpGifsApiClient httpGifsApiClient;

    @Autowired
    public CacheOperationService(DiskStorageRepository repository, GifOperationService service) {
        this.repository = repository;
    }

    public Cache getByQuery(String query) {
        return repository.getCacheByQuery(query).orElseThrow();
    }

    public List<Cache> getAll() {
        return repository.getCache();
    }

    public Cache createGif(GenerateCacheRequestDto dto) {
        httpGifsApiClient.getGif(dto.query);
        return getByQuery(dto.query);
    }

    public void deleteCache() {
        repository.deleteCache();
    }
}
