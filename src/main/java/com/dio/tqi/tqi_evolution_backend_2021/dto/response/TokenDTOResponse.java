package com.dio.tqi.tqi_evolution_backend_2021.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTOResponse {
    private String type;
    private String token;

    public String toJson() {
        ObjectMapper mapper = new JsonMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error at json convert "+ e);
        }
    }
}
