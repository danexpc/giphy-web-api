package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.entity.Gif;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GifToDtoMapper implements Mapper<GifResponseDto, Gif> {
    @Override
    public GifResponseDto map(Gif entity) {
        return new GifResponseDto(
                entity.getPath()
        );
    }

    @Override
    public List<GifResponseDto> mapCollection(List<Gif> gifs) {
        return gifs.stream().map(this::map).collect(Collectors.toList());
    }
}
