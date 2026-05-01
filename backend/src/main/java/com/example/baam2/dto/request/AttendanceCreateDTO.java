package com.example.baam2.dto.request;

import jakarta.validation.constraints.NotNull;

public record AttendanceCreateDTO (
        @NotNull(message ="Session id cannot be null")
        Long sessionId,

        @NotNull(message ="User id cannot be null")
        Long userId
){}