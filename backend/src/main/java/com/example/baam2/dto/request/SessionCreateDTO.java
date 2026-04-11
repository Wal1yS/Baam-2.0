package com.example.baam2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SessionCreateDTO (
    @NotBlank(message = "Session title cannot be null or empty")
    String title,

    @NotNull(message ="Session owner id cannot be null or empty")
    Long ownerId
){}
