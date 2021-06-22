package com.bsa.bsagiphy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class UserHistoryDto {
    private final Date date;
    private final String query;
    private final String gif;
}
