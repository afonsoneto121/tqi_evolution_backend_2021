package com.dio.tqi.tqi_evolution_backend_2021.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JsonUtil {
   public static String toJson(Object object) throws IOException {
       ObjectMapper objectMapper = new ObjectMapper();
       objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
       objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
       objectMapper.registerModules(new JavaTimeModule());

       return objectMapper.writeValueAsString(object);
    }
}
