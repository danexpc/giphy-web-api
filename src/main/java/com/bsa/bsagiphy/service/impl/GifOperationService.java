package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.repository.impl.DiskStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GifOperationService {

    private final DiskStorageRepository repository;

    @Autowired
    private HttpGifsApiClient httpGifsApiClient;

    @Autowired
    public GifOperationService(DiskStorageRepository repository) {
        this.repository = repository;
    }

    public List<String> getAll() {
        return repository.getFiles().stream().map(Gif::getPath).collect(Collectors.toList());
    }
}
