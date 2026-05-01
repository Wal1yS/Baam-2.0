package com.example.baam2.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(
    @NotBlank(message = "Password cannot be empty")
    String password
){}
