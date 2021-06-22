package com.bsa.bsagiphy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchGifRequestDto {
    private final String userId;
    private final String gifName;
}
