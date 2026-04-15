package com.example.baam2.controller;

import com.example.baam2.dto.request.AttendanceCreateDTO;
import com.example.baam2.dto.response.AttendanceResponseDTO;
import com.example.baam2.dto.response.UserAttendanceDTO;
import com.example.baam2.service.AttendanceService;
import com.example.baam2.service.QRTokenService;
import com.example.baam2.dto.request.QrScanRequestDTO;
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
    private final QRTokenService qrTokenService;

    public AttendanceController(AttendanceService attendanceService, QRTokenService qrTokenService) {
        this.attendanceService = attendanceService;
        this.qrTokenService = qrTokenService;
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

    @PostMapping("/scan")
    public ResponseEntity<String> scanQrCode(@Valid @RequestBody QrScanRequestDTO qrScanRequestDTO){
        Long sessionId = qrTokenService.validateTokenAndGetSesionId(qrScanRequestDTO.token());

        AttendanceCreateDTO createDTO = new AttendanceCreateDTO(
                sessionId,
                qrScanRequestDTO.userId()
        );

        attendanceService.createAttendance(createDTO);

        return ResponseEntity.ok().body("Success!");
    }
}