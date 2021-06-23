package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.CacheDto;
import com.bsa.bsagiphy.dto.GenerateCacheRequestDto;
import com.bsa.bsagiphy.entity.Cache;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CacheMapper {
    Cache cacheDtoToCache(CacheDto dto);

    CacheDto cacheToCacheDto(Cache entity);

    Cache generateCacheRequestDtoToCache(GenerateCacheRequestDto dto);

    List<CacheDto> listCacheToListCacheDto(List<Cache> entities);
}
