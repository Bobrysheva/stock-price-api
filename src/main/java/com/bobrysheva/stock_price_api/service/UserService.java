package com.bobrysheva.stock_price_api.service;

import com.bobrysheva.stock_price_api.dto.RefreshTokenDto;
import com.bobrysheva.stock_price_api.entity.User;
import com.bobrysheva.stock_price_api.exceptionsHandler.UserAlreadyExistsException;
import org.openapitools.model.AuthResponse;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.RegisterRequest;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public interface UserService {
    User createUser(RegisterRequest registerRequest) throws UserAlreadyExistsException;

    AuthResponse singIn(LoginRequest loginRequest) throws AuthenticationException;

    AuthResponse refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;
}
