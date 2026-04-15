package com.example.baam2.controller;

import com.example.baam2.dto.request.SessionCreateDTO;
import com.example.baam2.dto.request.SessionUpdateDTO;
import com.example.baam2.dto.response.SessionResponseDTO;
import com.example.baam2.model.UserModel;
import com.example.baam2.repository.SessionRepository;
import com.example.baam2.repository.UserRepository;
import com.example.baam2.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "http://localhost:5173")
public class SessionController {
    private final SessionService sessionService;
    private final UserRepository userRepository;

    public SessionController(SessionService sessionService, UserRepository userRepository, SessionRepository sessionRepository) {
        this.sessionService = sessionService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<SessionResponseDTO> createSession(@Valid @RequestBody SessionCreateDTO sessionCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.createSession(sessionCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSession(@PathVariable Long id,@Valid @RequestBody SessionUpdateDTO sessionUpdateDTO) {
        sessionService.updateSessionName(id, sessionUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/close/{id}")
    public ResponseEntity<Void> closeSession(@PathVariable Long id) {
        sessionService.closeSession(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionResponseDTO>> getAllSessions(){
        return ResponseEntity.ok().body(sessionService.getAllSessions());
    }

    @GetMapping("/alla")
    public ResponseEntity<List<Long>> getAllaSessions(){
        return ResponseEntity.ok().body(sessionService.getAllaSessions());
    }

    @PostMapping("/test")
    public ResponseEntity<Void> createUser(){
        UserModel userModel = new UserModel();
        userModel.setEmail("1234567890");
        userModel.setPassword("44444");
        userRepository.save(userModel);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/test")
    public ResponseEntity<Long> getNumber(){
        return ResponseEntity.ok().body(userRepository.count());
    }

}
