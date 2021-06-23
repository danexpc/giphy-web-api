package com.bsa.bsagiphy.dto;

import com.bsa.bsagiphy.entity.Gif;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CacheDto {
    private String query;
    private List<String> gifs;
}
