package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.CacheDto;
import com.bsa.bsagiphy.dto.GenerateCacheRequestDto;
import com.bsa.bsagiphy.mapper.CacheMapper;
import com.bsa.bsagiphy.service.impl.CacheOperationService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheOperationService service;
    private final CacheMapper mapper = Mappers.getMapper(CacheMapper.class);

    @Autowired
    public CacheController(CacheOperationService service) {
        this.service = service;
    }

    @GetMapping
    public List<CacheDto> queryCacheCollection(@RequestParam(required = false) Optional<String> query) {
        return query.map(q ->
                List.of(mapper.cacheToCacheDto(service.getCacheByQuery(q))))
                .orElseGet(() -> mapper.listCacheToListCacheDto(service.getAllCache()));
    }

    @PostMapping("/generate")
    public CacheDto createCache(@RequestBody GenerateCacheRequestDto generateCacheRequestDto) {
        return mapper.cacheToCacheDto(service.createGifInCache());
    }

    @DeleteMapping
    public void deleteCache() {
        service.deleteCache();
    }
}
