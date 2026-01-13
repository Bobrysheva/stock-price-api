package com.bobrysheva.stock_price_api.service;

import com.bobrysheva.stock_price_api.dto.RefreshTokenDto;
import com.bobrysheva.stock_price_api.entity.User;
import com.bobrysheva.stock_price_api.exceptionsHandler.UserAlreadyExistsException;
import com.bobrysheva.stock_price_api.mapper.UserMapper;
import com.bobrysheva.stock_price_api.repository.UserRepository;
import com.bobrysheva.stock_price_api.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AuthResponse;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public User createUser(RegisterRequest registerRequest) {
        log.info("Creating new User");
        Optional<User> findUserByEmail = userRepository.findUserByEmail(registerRequest.getEmail());
        if (findUserByEmail.isEmpty()) {
            User savedUser = userRepository.save(userMapper.mapToUser(registerRequest));
            log.info("User with ID: {} is created", savedUser.getId());
            return savedUser;
        }
        log.error("Failure to create user with duplicate email address");
        throw new UserAlreadyExistsException("User with email " + registerRequest.getEmail() + " already exists");
    }

    @Override
    public AuthResponse singIn(LoginRequest loginRequest) throws AuthenticationException {
        User user = findByCredentials(loginRequest);
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    private User findByCredentials(LoginRequest loginRequest) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findUserByEmail(loginRequest.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    private User findByEmail(String email) throws Exception {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
    }
}
