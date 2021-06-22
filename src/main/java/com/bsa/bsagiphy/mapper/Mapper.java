package com.bsa.bsagiphy.mapper;

import java.util.List;

public interface Mapper<T, U> {
    T map(U entity);

    List<T> mapCollection(List<U> entities);
}
