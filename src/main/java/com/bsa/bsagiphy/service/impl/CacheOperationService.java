package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.dto.GenerateCacheRequestDto;
import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.exception.EntityNotFoundException;
import com.bsa.bsagiphy.exception.InvalidArgumentException;
import com.bsa.bsagiphy.exception.UnavailableResourceException;
import com.bsa.bsagiphy.repository.DiskStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        try {
            return repository.getCacheByQuery(query).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(e);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Query argument is invalid", e);
        }
    }

    public List<Cache> getAll() {
        return repository.getCache();
    }

    public Cache createGif(GenerateCacheRequestDto dto) {
        try {
            httpGifsApiClient.getGif(dto.query);
            return getByQuery(dto.query);
        } catch (RuntimeException e) {
            throw new UnavailableResourceException(e);
        }
    }

    public void deleteCache() {
        try {
            repository.deleteCache();
        } catch (RuntimeException e) {
            throw new UnavailableResourceException(e);
        }
    }
}
