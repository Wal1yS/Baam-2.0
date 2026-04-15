package com.example.baam2.dto.response;
import java.time.LocalDateTime;

public record SessionResponseDTO (
    Long id,
    String title,
    String qrToken,
    LocalDateTime createdAt
){}