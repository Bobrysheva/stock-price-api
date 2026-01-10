package com.bobrysheva.stock_price_api.service;

import com.bobrysheva.stock_price_api.dto.JwtAuthenticationDto;
import com.bobrysheva.stock_price_api.dto.RefreshTokenDto;
import com.bobrysheva.stock_price_api.dto.UserCredentialsDto;
import com.bobrysheva.stock_price_api.exceptionsHandler.UserAlreadyExistsException;
import org.openapitools.model.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public interface UserService {
    ResponseEntity<?> createUser(RegisterRequest registerRequest) throws UserAlreadyExistsException;

    JwtAuthenticationDto singIn (UserCredentialsDto userCredentialsDto) throws AuthenticationException;

    JwtAuthenticationDto refreshToken (RefreshTokenDto refreshTokenDto) throws Exception;

}
