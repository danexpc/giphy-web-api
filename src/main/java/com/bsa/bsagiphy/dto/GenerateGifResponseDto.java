package com.bsa.bsagiphy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenerateGifResponseDto {
    private final String name;
    private final String pathToGif;
}
