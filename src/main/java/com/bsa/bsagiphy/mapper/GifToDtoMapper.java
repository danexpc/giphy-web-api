package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.entity.Gif;

public class GifToDtoMapper implements Mapper<GifResponseDto, Gif> {
    @Override
    public GifResponseDto map(Gif entity) {
        return new GifResponseDto(
                entity.getUrl()
        );
    }
}
