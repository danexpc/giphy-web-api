package com.bsa.bsagiphy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gif {

    private UUID id;
    private String name;
    private String path;
}
