package com.bsa.bsagiphy.mapper;

public interface Mapper<T, U> {
    T map(U entity);
}
