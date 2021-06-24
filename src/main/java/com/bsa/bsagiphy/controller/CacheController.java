package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.CacheDto;
import com.bsa.bsagiphy.dto.GenerateCacheRequestDto;
import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.mapper.CacheMapper;
import com.bsa.bsagiphy.service.impl.CacheOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheOperationService service;
    private final CacheMapper mapper;

    @Autowired
    public CacheController(CacheOperationService service, @Qualifier("cacheMapper") CacheMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<CacheDto> queryCacheCollection(@RequestParam(required = false) Optional<String> query) {
        return query.map(q ->
                List.of(mapper.cacheToCacheDto(service.getByQuery(q).orElse(new Cache()))))
                .orElseGet(() -> mapper.listCacheToListCacheDto(service.getAll()));
    }

    @PostMapping("/generate")
    public CacheDto createCache(@RequestBody GenerateCacheRequestDto dto) {
        return mapper.cacheToCacheDto(service.createGif(dto).orElse(new Cache()));
    }

    @DeleteMapping
    public void deleteCache() {
        service.deleteCache();
    }
}
