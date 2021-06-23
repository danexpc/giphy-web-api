package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.GenerateGifForUserDto;
import com.bsa.bsagiphy.dto.GifDto;
import com.bsa.bsagiphy.dto.GifRequestDto;
import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.entity.Gif;
import org.mapstruct.Mapper;

@Mapper
public interface GifMapper {

    GifDto GifToGifDto(Gif entity);

    Gif GifDtoToGif(GifDto dto);

    Gif GifRequestDtoToGif(GifRequestDto dto);

    GifResponseDto GifToGifResponseDto(Gif entity);

    Gif GenerateGifForUserDtoToGif(GenerateGifForUserDto dto);
}
