package com.dio.tqi.tqi_evolution_backend_2021.service;

import com.dio.tqi.tqi_evolution_backend_2021.exception.NotAuthorizedException;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.exception.UserAlreadyExists;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.repository.UserRepository;
import com.dio.tqi.tqi_evolution_backend_2021.util.SecurityUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private SecurityUtil securityUtil;
    @InjectMocks
    private UserService userService;

    private UserModel userModel;
    private UserModel userModelResult;
    @BeforeEach
    public void setup() {
        userModel = UserModel.builder()
                .name("Test Name")
                .address("Test Address")
                .cpf("123456789")
                .email("test@test.com")
                .password("1234")
                .rg("987654321")
                .income(500.0F)
                .build();
        userModelResult = userModel;
        userModelResult.setId("1");
    }
    @Test
    public void whenUserSaveThenUserShouldBeSaved() throws UserAlreadyExists {
        Mockito.when(repository.save(userModel)).thenReturn(userModelResult);
        Mockito.when(repository.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        Mockito.when(encoder.encode(userModel.getPassword())).thenReturn(userModel.getPassword());

        UserModel save = userService.save(userModel);

        assertThat(save, Matchers.is(Matchers.equalTo(userModelResult)));
    }
    @Test
    public void whenUserAlreadyExistsThenAnExceptionShouldBeThrown() {
        Mockito.when(repository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(userModel));

        assertThrows(UserAlreadyExists.class, () -> userService.save(userModel));
    }

    @Test
    public void whenUserUpdateThenUserShouldBeUpdated() throws NotAuthorizedException, UserAlreadyExists, NotFound {
        HttpServletRequest request = new MockHttpServletRequest();

        userModelResult.setName("New Name");
        Mockito.doNothing().when(securityUtil).authorizedUser(request,userModel.getId());
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(userModelResult));
        Mockito.when(repository.save(userModel)).thenReturn(userModelResult);

        UserModel update = userService.update("1", userModel, request);

        assertThat(update.getName(),Matchers.equalTo("New Name"));

    }
    @Test
    public void whenEmailAlreadyExistInUpdateThenAnExceptionShouldBeThrown() throws NotAuthorizedException, UserAlreadyExists, NotFound {
        HttpServletRequest request = new MockHttpServletRequest();

        userModel.setEmail("new@mail.com");

        Mockito.doNothing().when(securityUtil).authorizedUser(request,userModel.getId());
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(UserModel.builder().email("other@mail.com").build()));
        Mockito.when(repository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(new UserModel()));

        assertThrows(UserAlreadyExists.class, () ->
                userService.update("1", userModel, request));

    }
    @Test
    public void whenUserTokenDifferentThenAnExceptionShouldBeThrown() throws NotAuthorizedException, UserAlreadyExists, NotFound {
        HttpServletRequest request = new MockHttpServletRequest();

        userModel.setName("new@mail.com");
        Mockito.doThrow(new NotAuthorizedException("")).when(securityUtil).authorizedUser(request,userModel.getId());

        assertThrows(NotAuthorizedException.class, () -> userService.update("1", userModel, request));
    }

    @Test
    public void whenValidIdThenUserShouldReturned() throws NotAuthorizedException, NotFound {
        HttpServletRequest request = new MockHttpServletRequest();

        Mockito.doNothing().when(securityUtil).authorizedUser(request,userModel.getId());
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(userModelResult));

        UserModel byId = userService.findById("1", request);

        assertThat(byId, Matchers.equalTo(userModelResult));
    }
    @Test
    public void whenInvalidIdThenAnExceptionShouldBeThrown() throws NotAuthorizedException, NotFound {
        HttpServletRequest request = new MockHttpServletRequest();

        Mockito.doNothing().when(securityUtil).authorizedUser(request,userModel.getId());
        Mockito.when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> userService.findById("1", request));
    }
}
