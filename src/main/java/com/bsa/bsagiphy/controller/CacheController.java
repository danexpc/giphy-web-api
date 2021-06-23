package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.CacheDto;
import com.bsa.bsagiphy.dto.GenerateCacheRequestDto;
import com.bsa.bsagiphy.service.CacheOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheOperationService cacheOperationService;

    @Autowired
    public CacheController(CacheOperationService cacheOperationService) {
        this.cacheOperationService = cacheOperationService;
    }

    @GetMapping
    public List<CacheDto> queryCacheCollection(@RequestParam String query) {
        // todo
        return null;
    }

    @PostMapping("/generate")
    public CacheDto createCache(@RequestBody GenerateCacheRequestDto generateCacheRequestDto) {
        // todo
        return null;
    }

    @DeleteMapping
    public void deleteCache() {
        // todo
    }
}
