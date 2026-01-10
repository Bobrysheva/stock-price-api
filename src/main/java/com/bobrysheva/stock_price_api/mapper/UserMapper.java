package com.bobrysheva.stock_price_api.mapper;

import com.bobrysheva.stock_price_api.entity.User;
import org.openapitools.model.RegisterRequest;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User mapToUser(RegisterRequest registerRequest) {
        return User.builder()
                .login(registerRequest.getLogin())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
    }
}
