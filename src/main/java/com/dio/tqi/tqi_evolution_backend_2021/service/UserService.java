package com.dio.tqi.tqi_evolution_backend_2021.service;

import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.exception.UserAlreadyExists;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import com.dio.tqi.tqi_evolution_backend_2021.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserModel save(UserModel userModel) throws UserAlreadyExists {
        this.userAlreadyExist(userModel.getEmail());
        return userRepository.save(userModel);
    }
    private void userAlreadyExist(String email) throws UserAlreadyExists {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new UserAlreadyExists("User Already Exists"));
    }
    public UserModel update(String id, UserModel userModel) throws NotFound, UserAlreadyExists {
        this.userAlreadyExist(userModel.getEmail());
        this.findById(id);
        userRepository.save(userModel);
        return null;
    }
    public void delete(String id) {
        userRepository.deleteById(id);
    }
    public UserModel findById(String id) throws NotFound {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFound("User Not Found"));
    }
}
