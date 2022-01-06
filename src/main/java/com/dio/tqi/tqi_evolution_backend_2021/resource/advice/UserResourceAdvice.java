package com.dio.tqi.tqi_evolution_backend_2021.resource.advice;

import com.dio.tqi.tqi_evolution_backend_2021.dto.Message;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotAuthorizedException;
import com.dio.tqi.tqi_evolution_backend_2021.exception.NotFound;
import com.dio.tqi.tqi_evolution_backend_2021.exception.UserAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserResourceAdvice {
    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Message> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          Message.builder().message("User Not Found").build()
        );
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Message> userAlreadyExists() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Message.builder().message("Email Already Exists").build()
        );
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<Message> notAuthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Message.builder().message("User not has permission").build()
        );
    }
}
