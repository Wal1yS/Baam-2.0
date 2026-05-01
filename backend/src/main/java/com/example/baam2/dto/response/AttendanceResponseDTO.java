package com.example.baam2.dto.response;
import java.time.LocalDateTime;

public record AttendanceResponseDTO (
        Long id,
        LocalDateTime timestamp
){}
