package com.dio.tqi.tqi_evolution_backend_2021.service;

import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.exception.UserAlreadyExists;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

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
    public UserModel update(String id, UserModel userModel) throws NotFound, UserAlreadyExists {
        UserModel byId = this.findById(id);
        if (!byId.getEmail().equals(userModel.getEmail())) {
            this.userAlreadyExist(userModel.getEmail());
        }
        userModel.setId(byId.getId());
        return userRepository.save(userModel);
    }
    public void delete(String id) {
        userRepository.deleteById(id);
    }
    public UserModel findById(String id) throws NotFound {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFound("User Not Found"));
    }
}
