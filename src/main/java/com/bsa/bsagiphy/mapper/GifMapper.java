package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.GenerateGifForUserDto;
import com.bsa.bsagiphy.dto.GifDto;
import com.bsa.bsagiphy.dto.GifRequestDto;
import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.entity.Gif;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GifMapper {

    GifDto gifToGifDto(Gif entity);

    Gif gifDtoToGif(GifDto dto);

    Gif gifRequestDtoToGif(GifRequestDto dto);

    GifResponseDto gifToGifResponseDto(Gif entity);

    Gif generateGifForUserDtoToGif(GenerateGifForUserDto dto);

    List<GifResponseDto> listGifToListGifDto(List<Gif> entities);

    List<Gif> listGifDtoToListGif(List<GifDto> dtos);
}
