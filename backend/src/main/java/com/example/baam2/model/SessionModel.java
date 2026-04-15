package com.example.baam2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sessions")
@AllArgsConstructor
@NoArgsConstructor
public class SessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserModel owner;
    private boolean isActive;
    private LocalDateTime createAt;
}


