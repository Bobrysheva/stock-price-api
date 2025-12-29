package com.bobrysheva.stock_price_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest<message>(

        @NotBlank(message = "Email не должно быть пустым")
        @Email(message = "Введите корректный email")
        String email,

        @NotBlank(message = "Придумайте корректный логин")
        @Size(min = 2, message = "Логин должен быть не короче 2 символов")
        String login,

        @NotBlank
        @Size(min = 6, message = "Пароль должен быть минимум 6 символов")
        String password) {
}

