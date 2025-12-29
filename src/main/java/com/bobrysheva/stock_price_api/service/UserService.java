package com.bobrysheva.stock_price_api.service;

import com.bobrysheva.stock_price_api.dto.RegisterRequest;
import com.bobrysheva.stock_price_api.entity.User;
import com.bobrysheva.stock_price_api.exceptionsHandler.UserAlreadyExistsException;
import com.bobrysheva.stock_price_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> createUser(RegisterRequest registerRequest) {
        log.info("Creating new User");
        Optional<User> findUserByEmail = userRepository.findUserByEmail(registerRequest.email());
        if (findUserByEmail.isEmpty()) {
            User user = new User();
            user.setLogin(registerRequest.login());
            user.setEmail(registerRequest.email());
            user.setPassword(registerRequest.password());
            User savedUser = userRepository.save(user);
            log.info("User wwith ID: {} is created", savedUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        }
        log.error("Failure to create user with duplicate email address");
        throw new UserAlreadyExistsException("User with email " + registerRequest.email() + " already exists");
    }
}
