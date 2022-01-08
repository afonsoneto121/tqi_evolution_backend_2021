package com.dio.tqi.tqi_evolution_backend_2021.resource;

import com.dio.tqi.tqi_evolution_backend_2021.dto.request.UserDTORequest;
import com.dio.tqi.tqi_evolution_backend_2021.dto.response.UserDTOResponse;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.mapper.UserMapper;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.resource.advice.UserResourceAdvice;
import com.dio.tqi.tqi_evolution_backend_2021.service.UserService;
import com.dio.tqi.tqi_evolution_backend_2021.util.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.dio.tqi.tqi_evolution_backend_2021.util.GenerateObjects.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserResourceTest {
    private MockMvc mvc;

    @Mock
    private UserService service;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserResource userResource;

    private UserModel userModel;
    private UserDTORequest dtoRequest;
    private UserDTOResponse dtoResponse;
    @BeforeEach
    public void setup() {

        mvc = MockMvcBuilders.standaloneSetup(userResource)
                .setControllerAdvice(new UserResourceAdvice())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
        userModel = getUserModel();
        dtoRequest = getUserDTORequest();
        dtoResponse = getUserDTOResponse();
    }
    /*
    Given right user
    When save user
    Then Should be return with status 201 CREATED
         and
         user in payload without password
     */
    @Test
    public void saveValidUser() throws Exception {
        Mockito.when(service.save(userModel)).thenReturn(userModel);
        Mockito.when(mapper.dtoRequestToModel(dtoRequest)).thenReturn(userModel);
        Mockito.when(mapper.modelToDtoResponse(userModel)).thenReturn(dtoResponse);
        mvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(dtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.equalTo(dtoResponse.getName())))
                .andExpect(jsonPath("$.id", Matchers.equalTo(dtoResponse.getId())));
    }
    /*
    Given a bad format user
    When save user
    Then Should be return with status 400 BAD REQUEST
     */
    @Test
    public void saveInvalidUser() throws Exception {
        UserDTORequest badRequest = UserDTORequest.builder().email("teste@teste.com").build();
        mvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(badRequest)))
                .andExpect(status().isBadRequest());
    }

    /*
    Given right user
    When update user
    Then Should be return with status 200 OK
     */
    @Test
    public void updateValidUser() throws Exception {
        Mockito.when(mapper.modelToDtoResponse(userModel)).thenReturn(dtoResponse);
        Mockito.when(mapper.dtoRequestToModel(dtoRequest)).thenReturn(userModel);
        mvc.perform(put("/api/v1/users/" + userModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(dtoRequest))
                        .with(request1 -> {
                            try {
                                Mockito.when(service.update(userModel.getId(), userModel, request1)).thenReturn(userModel);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return request1;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo(dtoResponse.getName())))
                .andExpect(jsonPath("$.id", Matchers.equalTo(dtoResponse.getId())));
    }

    /*
    Given  user id
    When delete user
    Then Should be return with status 204 NO CONTENT
     */
    @Test
    public void deleteUser() throws Exception {
        mvc.perform(delete("/api/v1/users/" + userModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(dtoRequest))
                        .with(request -> {
                            try {
                                Mockito.doNothing().when(service).delete(userModel.getId(), request);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return request;
                        }))
                .andExpect(status().isNoContent());
    }
    /*
    Given a valid id
    When request in find by id
    Then user should be returned
     */
    @Test
    public void getValidId() throws Exception {
        Mockito.when(mapper.modelToDtoResponse(userModel)).thenReturn(dtoResponse);
        mvc.perform(get("/api/v1/users/"+ userModel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            try {
                                Mockito.when(service.findById(userModel.getId(),request)).thenReturn(userModel);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo(dtoResponse.getName())))
                .andExpect(jsonPath("$.id", Matchers.equalTo(dtoResponse.getId())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(dtoResponse.getEmail())));
    }

    /*
    Given an invalid id
    When request in find by id
    Then user should be returned
     */
    @Test
    public void getInvalidId() throws Exception {
        mvc.perform(get("/api/v1/users/"+ userModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(request -> {
                            try {
                                Mockito.when(service.findById(userModel.getId(),request)).thenThrow(NotFound.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return request;
                        }))
                .andExpect(status().isNotFound());
    }
}
