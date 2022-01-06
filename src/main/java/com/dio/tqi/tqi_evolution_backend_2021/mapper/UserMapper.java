package com.dio.tqi.tqi_evolution_backend_2021.mapper;

import com.dio.tqi.tqi_evolution_backend_2021.dto.request.UserDTORequest;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.UserDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ObjectMapper mapper;

    public UserModel dtoRequestToModel(UserDTORequest userDTORequest) {
        return mapper.convertValue(userDTORequest, UserModel.class);
    }
    public UserDTORequest modelToDtoRequest(UserModel userModel) {
        return mapper.convertValue(userModel,UserDTORequest.class);
    }
    public UserModel dtoResponseToModel(UserDTOResponse userDTOResponse) {
        return mapper.convertValue(userDTOResponse, UserModel.class);
    }
    public UserDTOResponse modelToDtoResponse(UserModel userModel) {
        return mapper.convertValue(userModel,UserDTOResponse.class);
    }


}
