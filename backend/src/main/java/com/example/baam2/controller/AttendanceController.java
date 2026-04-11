package com.example.baam2.controller;

import com.example.baam2.dto.request.AttendanceCreateDTO;
import com.example.baam2.dto.request.SessionCreateDTO;
import com.example.baam2.dto.request.SessionUpdateDTO;
import com.example.baam2.dto.response.AttendanceResponseDTO;
import com.example.baam2.dto.response.SessionResponseDTO;
import com.example.baam2.dto.response.UserAttendanceDTO;
import com.example.baam2.model.UserModel;
import com.example.baam2.repository.SessionRepository;
import com.example.baam2.repository.UserRepository;
import com.example.baam2.service.AttendanceService;
import com.example.baam2.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "http://localhost:5173")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/create")
    public ResponseEntity<AttendanceResponseDTO> createAttendance(@Valid @RequestBody AttendanceCreateDTO attendanceCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.createAttendance(attendanceCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<UserAttendanceDTO>> getAllUserAttendance(@PathVariable Long id){
        return ResponseEntity.ok().body(attendanceService.getAllUserAttendance(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceResponseDTO>> getAllAttendance(){
        return ResponseEntity.ok().body(attendanceService.getAllAttendance());
    }

}