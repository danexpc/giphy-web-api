package com.bsa.bsagiphy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.http.HttpClient;

@Configuration
public class Configuration {

    @Bean
    @Scope(value = "prototype")
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }
}
