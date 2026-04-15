package com.example.baam2.controller;

import com.example.baam2.dto.request.UserCreateDTO;
import com.example.baam2.dto.request.UserLoginDTO;
import com.example.baam2.dto.request.UserUpdateDTO;
import com.example.baam2.dto.response.UserDTO;
import com.example.baam2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.example.baam2.service.EmailService;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    // just for testing
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> signIn(@Valid @RequestBody UserLoginDTO loginDTO, HttpServletRequest request) { // Замените Object на LoginDTO и AuthResponseDTO
        UserDTO response = userService.loginUser(loginDTO);

        //old session invalidation logic
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null) {
            existingSession.invalidate();
        }
        //new session creation logic
        HttpSession session = request.getSession(true);
        session.setAttribute("id", response.id());

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
            response.id().toString(),
            null,
            Collections.emptyList());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return ResponseEntity.ok()
                .body(userService.loginUser(loginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserDTO response = userService.createUser(userCreateDTO);
        // just for testing
        try {
            emailService.sendWelcomeEmail(response.id());
        } catch (Exception ex) {
            logger.warn("User {} created, but welcome email was not sent: {}", response.id(), ex.getMessage());
        }

        return ResponseEntity.status(201)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(401).build();
        }

        Long userId = (Long) session.getAttribute("id");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(userService.getUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
