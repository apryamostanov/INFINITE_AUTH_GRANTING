package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class T_jackson_config {

    @Bean
    ObjectMapper p_object_mapper() {
        ObjectMapper mapper = new ObjectMapper()
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        return mapper
    }


    @Bean
    ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper()
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        return mapper
    }
}