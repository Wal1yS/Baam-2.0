package com.example.baam2.controller;

import com.example.baam2.dto.request.SessionCreateDTO;
import com.example.baam2.dto.request.SessionUpdateDTO;
import com.example.baam2.dto.response.SessionResponseDTO;
import com.example.baam2.model.UserModel;
import com.example.baam2.repository.SessionRepository;
import com.example.baam2.repository.UserRepository;
import com.example.baam2.service.SessionService;
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
    public SessionResponseDTO createSession(@RequestBody SessionCreateDTO sessionCreateDTO) {
        return sessionService.createSession(sessionCreateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
    }

    @PatchMapping("/{id}")
    public void updateSession(@PathVariable Long id,@RequestBody SessionUpdateDTO sessionUpdateDTO) {
        sessionService.updateSessionName(id, sessionUpdateDTO);
    }

    @GetMapping("/all")
    public List<SessionResponseDTO> getAllSessions(){
        return sessionService.getAllSessions();
    }

    @PostMapping("/test")
    public void createUser(){
        UserModel userModel = new UserModel();
        userModel.setEmail("1234567890");
        userModel.setPassword("44444");
        userRepository.save(userModel);
    }

    @GetMapping("/test")
    public long getNumber(){
        return userRepository.count();
    }

}
