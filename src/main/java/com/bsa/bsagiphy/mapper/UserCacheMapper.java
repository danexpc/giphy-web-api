package com.bsa.bsagiphy.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCacheMapper extends CacheMapper, UserHistoryMapper, GifMapper{
}
