package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.CacheDto;
import com.bsa.bsagiphy.dto.GenerateCacheRequestDto;
import com.bsa.bsagiphy.entity.Cache;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CacheMapper {
    Cache CacheDtoToCache(CacheDto dto);

    CacheDto CacheToCacheDto(Cache entity);

    Cache GenerateCacheRequestDtoToCache(GenerateCacheRequestDto dto);
}
