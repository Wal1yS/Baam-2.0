package com.example.baam2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QrScanRequestDTO (
        @NotBlank(message = "Token cannot be empty")
        String token,

        @NotNull(message = "User id cannot be empty")
        Long userId
){}
