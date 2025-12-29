package com.bobrysheva.stock_price_api.сontroller;

import com.bobrysheva.stock_price_api.dto.RegisterRequest;
import com.bobrysheva.stock_price_api.entity.User;
import com.bobrysheva.stock_price_api.repository.UserRepository;
import com.bobrysheva.stock_price_api.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Managing users and their requests")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity <?> regiserUser (@RequestBody RegisterRequest registerRequest) {
        return userService.createUser(registerRequest);
    }
//    private ResponseEntity<User> createUser (@RequestBody RegisterRequest registerRequest) {
//        User user = new User();
//        user.setLogin(registerRequest.login());
//        user.setEmail(registerRequest.email());
//        user.setPassword(registerRequest.password());
//        return ResponseEntity.ok().body(userRepository.save(user));
//    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PatchMapping
    public ResponseEntity <User> updateUser (@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setLogin(registerRequest.login());
        user.setEmail(registerRequest.email());
        user.setPassword(registerRequest.password());
        return ResponseEntity.ok().body(userRepository.save(user));
    }


}
