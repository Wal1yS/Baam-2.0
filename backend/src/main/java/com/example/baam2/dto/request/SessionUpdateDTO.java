package com.example.baam2.dto.request;


import jakarta.validation.constraints.NotBlank;

public record SessionUpdateDTO (
        @NotBlank(message = "Session title cannot be null or empty")
        String title
){}
