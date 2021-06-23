package com.bsa.bsagiphy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserHistoryDto {
    private Date date;
    private String query;
    private String gif;
}
