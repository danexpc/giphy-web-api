package com.bsa.bsagiphy.dto;

import com.bsa.bsagiphy.entity.Gif;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CacheDto {
    private final String query;
    private final List<Gif> gifs;
}
