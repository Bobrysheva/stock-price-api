package com.bobrysheva.stock_price_api.service;

import com.bobrysheva.stock_price_api.entity.User;
import com.bobrysheva.stock_price_api.exceptionsHandler.UserAlreadyExistsException;
import com.bobrysheva.stock_price_api.mapper.UserMapper;
import com.bobrysheva.stock_price_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserMapper userMapper;
    private RegisterRequest validRegisterRequest;
    private User savedUser;
    private User existingUser;

    @BeforeEach
    public void setUp() {

        validRegisterRequest = RegisterRequest.builder()
                .login("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        existingUser = User.builder()
                .id(1L)
                .login("existinguser")
                .email("test@example.com")
                .password("oldpassword")
                .build();

        savedUser = User.builder()
                .id(2L)
                .login("testuser")
                .email("test@example.com")
                .password("password123")
                .build();
    }

    @Test
    @DisplayName("New user suссessful creation")
    public void createUser_thatNotExistsYet() {

        when(userRepository.findUserByEmail(validRegisterRequest.getEmail())).thenReturn(Optional.empty());
        when(userMapper.mapToUser(any())).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userServiceImpl.createUser(validRegisterRequest);

        assertEquals(savedUser.getId(), result.getId());

        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getLogin(), result.getLogin());
        assertEquals(savedUser.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findUserByEmail(validRegisterRequest.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Unsuccessful creation of a user with a registered email - should throw an exception")
    public void createUser_WhenEmailAlreadyExist_ShouldThrowException() {
        when(userRepository.findUserByEmail(validRegisterRequest.getEmail())).thenReturn(Optional.of(existingUser));

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> userServiceImpl.createUser(validRegisterRequest));

        assertEquals("User with email " + validRegisterRequest.getEmail() + " already exists", exception.getMessage());

    }
}