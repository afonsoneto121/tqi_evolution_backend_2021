package com.dio.tqi.tqi_evolution_backend_2021.repository;

import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String s);
}
