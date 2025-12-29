package com.bobrysheva.stock_price_api.service;
//
//import com.bobrysheva.stock_price_api.dto.RegisterRequest;
//import com.bobrysheva.stock_price_api.entity.User;
//import com.bobrysheva.stock_price_api.exceptionsHandler.UserAlreadyExistsException;
//import com.bobrysheva.stock_price_api.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class UserService {
//
//
//    private final UserRepository userRepository;
//
//    public ResponseEntity<User> createUser(RegisterRequest registerRequest) {
//
//        log.info("Creating new User");
//
//        Optional<User> findUserByEmail = userRepository.findUserByEmail(registerRequest.email());
//        if (findUserByEmail.isEmpty()) {
//            User user = new User();
//            user.setLogin(registerRequest.login());
//            user.setEmail(registerRequest.email());
//            user.setPassword(registerRequest.password());
//            return ResponseEntity.status(201).body(userRepository.save(user));
//        }
//        log.error("Failure to create user with duplicate email address");
//        throw new UserAlreadyExistsException("User with email " + registerRequest.email() + " already exists");
//    }
//}
