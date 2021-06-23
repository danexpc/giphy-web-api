package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.mapper.GifMapper;
import com.bsa.bsagiphy.service.impl.GifOperationService;
import org.mapstruct.factory.Mappers;
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

    private final GifOperationService service;
    private final GifMapper mapper = Mappers.getMapper(GifMapper.class);

    @Autowired
    public GifController(GifOperationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GifResponseDto>> getAllGifs() {
        var gifs = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(mapper.listGifToListGifDto(gifs));
    }
}
