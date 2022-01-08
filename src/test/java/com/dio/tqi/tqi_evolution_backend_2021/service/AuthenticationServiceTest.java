package com.dio.tqi.tqi_evolution_backend_2021.service;

import com.dio.tqi.tqi_evolution_backend_2021.entity.Login;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.repository.UserRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    UserRepository repository;

    @InjectMocks
    AuthenticationService service;
    private UserModel userModel;
    private Login login;

    @BeforeEach
    public void setup () {
        userModel = UserModel.builder()
                .id("1")
                .name("Test Name")
                .address("Test Address")
                .cpf("123456789")
                .email("test@test.com")
                .password("1234")
                .rg("987654321")
                .income(500.0F)
                .build();

        login = new Login(Optional.of(userModel));
    }
    /*
    When a valid username is passed, then should be created a Login
     */
    @Test
    public void validUsername() {
        Mockito.when(repository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(userModel));

        UserDetails userDetails = service.loadUserByUsername(userModel.getEmail());

        MatcherAssert.assertThat(userDetails.getUsername(), Matchers.equalTo(login.getUsername()));
        MatcherAssert.assertThat(userDetails.getPassword(), Matchers.equalTo(login.getPassword()));
    }

    /*
    When a invalid username is passed, then an exceptions should be thrown
     */
    @Test
    public void invalidUsername() {
        Mockito.when(repository.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class,() -> service.loadUserByUsername(userModel.getEmail()));

    }
}
