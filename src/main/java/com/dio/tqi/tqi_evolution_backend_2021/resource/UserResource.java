package com.dio.tqi.tqi_evolution_backend_2021.resource;

import com.dio.tqi.tqi_evolution_backend_2021.dto.request.UserDTORequest;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.UserDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.exception.UserAlreadyExists;
import com.dio.tqi.tqi_evolution_backend_2021.mapper.UserMapper;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserDTOResponse> save(@RequestBody @Valid UserDTORequest userDTORequest) throws UserAlreadyExists {
        UserModel userModel = mapper.dtoRequestToModel(userDTORequest);
        UserModel save = service.save(userModel);
        UserDTOResponse userDTOResponse = mapper.modelToDtoResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTOResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTOResponse> update(@RequestParam String id,
                                 @RequestBody @Valid UserDTORequest userDTORequest) throws UserAlreadyExists, NotFound {
        UserModel userModel = mapper.dtoRequestToModel(userDTORequest);
        UserModel update = service.update(id, userModel);
        UserDTOResponse userDTOResponse = mapper.modelToDtoResponse(update);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTOResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestParam String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTOResponse> findById(@RequestParam String id) throws NotFound {
        UserModel byId = service.findById(id);
        UserDTOResponse userDTOResponse = mapper.modelToDtoResponse(byId);
        return ResponseEntity.ok(userDTOResponse);
    }
}
