package com.bsa.bsagiphy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenerateGifForUserDto {
    private final String query;
    private final Boolean force;
}
