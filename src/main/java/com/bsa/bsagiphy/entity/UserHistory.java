package com.bsa.bsagiphy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHistory {
    private Date date;
    private String query;
    private String gif;
}
