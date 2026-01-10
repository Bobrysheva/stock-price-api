package com.bobrysheva.stock_price_api.mapper;

import com.bobrysheva.stock_price_api.entity.User;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    public User mapToUser(RegisterRequest registerRequest) {
        return User.builder()
                .login(registerRequest.getLogin())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
    }
}
