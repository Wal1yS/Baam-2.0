package com.example.baam2.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
    @NotBlank(message = "Email cannot be empty")
    String email,
    @NotBlank(message = "Password cannot be empty")
    String password
){}
