package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.entity.Gif;

import java.util.List;
import java.util.stream.Collectors;

public class GifToDtoMapper implements Mapper<GifResponseDto, Gif> {
    @Override
    public GifResponseDto map(Gif entity) {
        return new GifResponseDto(
                entity.getUrl()
        );
    }

    @Override
    public List<GifResponseDto> mapCollection(List<Gif> gifs) {
        return gifs.stream().map(this::map).collect(Collectors.toList());
    }
}
