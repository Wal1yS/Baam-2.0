package com.example.baam2.service;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.properties.mail.smtp.from:${spring.mail.username}}")
    private String fromEmail;

    public void sendWelcomeEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Baam 2.0 Registration Successful");
        message.setText("Hello " + email + ",\n\nWelcome to Baam 2.0!");
        mailSender.send(message);
    }

}
