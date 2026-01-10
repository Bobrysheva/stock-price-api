package com.bobrysheva.stock_price_api.controller;

import com.bobrysheva.stock_price_api.dto.JwtAuthenticationDto;
import com.bobrysheva.stock_price_api.dto.RefreshTokenDto;
import com.bobrysheva.stock_price_api.dto.UserCredentialsDto;
import com.bobrysheva.stock_price_api.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Managing users and their requests")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> regiserUser(@RequestBody RegisterRequest registerRequest) {
        return userService.createUser(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.singIn(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed" + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh (@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return userService.refreshToken(refreshTokenDto);
    }
}
