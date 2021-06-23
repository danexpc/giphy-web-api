package com.bsa.bsagiphy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateGifForUserDto {
    private String query;
    private Boolean force;
}
