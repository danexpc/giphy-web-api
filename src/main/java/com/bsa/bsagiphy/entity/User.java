package com.bsa.bsagiphy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private final UUID id;
}
