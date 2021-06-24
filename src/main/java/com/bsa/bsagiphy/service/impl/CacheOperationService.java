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

    @Autowired
    private HttpGifsApiClient httpGifsApiClient;

    @Autowired
    public CacheOperationService(DiskStorageRepository repository, GifOperationService service) {
        this.repository = repository;
    }

    public Optional<Cache> getByQuery(String query) {
        return repository.getCacheByQuery(query);
    }

    public List<Cache> getAll() {
        return repository.getCache();
    }

    public Optional<Cache> createGif(GenerateCacheRequestDto dto) {
        httpGifsApiClient.getGif(dto.query);
        return getByQuery(dto.query);
    }

    public void deleteCache() {
        repository.deleteCache();
    }
}
