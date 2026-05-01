package com.example.baam2.dto.request;

import jakarta.validation.constraints.NotNull;

public record AttendanceGpsCreateDTO(
        @NotNull(message ="Session id cannot be null")
        Long sessionId,

        @NotNull(message ="User id cannot be null")
        Long userId,

        @NotNull(message ="Latitude id cannot be null")
        Double latitude,

        @NotNull(message ="Longitude id cannot be null")
        Double longitude
){}