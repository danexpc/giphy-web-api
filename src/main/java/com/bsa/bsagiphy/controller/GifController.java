package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.mapper.GifToDtoMapper;
import com.bsa.bsagiphy.mapper.Mapper;
import com.bsa.bsagiphy.service.impl.GifOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gifs")
public class GifController {

    private final GifOperationService gifOperationService;
    private final Mapper<GifResponseDto, Gif> mapper;

    @Autowired
    public GifController(GifOperationService gifOperationService, GifToDtoMapper mapper) {
        this.gifOperationService = gifOperationService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<GifResponseDto>> getAllGifs() {
        var gifs = gifOperationService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapCollection(gifs));
    }
}
