package com.dio.tqi.tqi_evolution_backend_2021.service;

import com.dio.tqi.tqi_evolution_backend_2021.exception.NotAuthorizedException;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.exception.UserAlreadyExists;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.repository.UserRepository;
import com.dio.tqi.tqi_evolution_backend_2021.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final SecurityUtil securityUtil;
    public UserModel save(UserModel userModel) throws UserAlreadyExists {
        this.userAlreadyExist(userModel.getEmail());
        userModel.setPassword(encoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }
    private void userAlreadyExist(String email) throws UserAlreadyExists {
        Optional<UserModel> optional = userRepository.findByEmail(email);
        if(optional.isPresent()){
            throw new UserAlreadyExists("User Already Exists");
        }
    }
    public UserModel update(String id, UserModel userModel, HttpServletRequest request) throws NotFound, UserAlreadyExists, NotAuthorizedException {
        securityUtil.authorizedUser(request,id);
        UserModel byId = this.findById(id, request);
        if (!byId.getEmail().equals(userModel.getEmail())) {
            this.userAlreadyExist(userModel.getEmail());
        }
        userModel.setId(byId.getId());
        return userRepository.save(userModel);
    }
    public void delete(String id, HttpServletRequest request) throws NotAuthorizedException {
        securityUtil.authorizedUser(request,id);
        userRepository.deleteById(id);
    }
    public UserModel findById(String id, HttpServletRequest request) throws NotFound, NotAuthorizedException {
        securityUtil.authorizedUser(request,id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFound("User Not Found"));
    }
}
