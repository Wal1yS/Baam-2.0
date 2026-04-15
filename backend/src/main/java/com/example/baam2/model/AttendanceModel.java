package com.example.baam2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attendance_logs")
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private SessionModel session;

    private LocalDateTime timestamp;
}