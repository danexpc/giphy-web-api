package com.bsa.bsagiphy.config;

import com.bsa.bsagiphy.mapper.CacheMapper;
import com.bsa.bsagiphy.mapper.UserCacheMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.http.HttpClient;

@Configuration
public class ApiConfiguration {

    @Bean
    @Scope(value = "prototype")
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public CacheMapper cacheMapper() {
        return  Mappers.getMapper(CacheMapper.class);
    }

    @Bean
    public UserCacheMapper userMapper() {
        return  Mappers.getMapper(UserCacheMapper.class);
    }
}
