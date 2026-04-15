package com.example.baam2.service;

import com.example.baam2.controller.SessionController;
import com.example.baam2.dto.request.SessionCreateDTO;
import com.example.baam2.dto.request.SessionUpdateDTO;
import com.example.baam2.repository.AttendanceRepository;
import com.example.baam2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.baam2.dto.response.SessionResponseDTO;
import com.example.baam2.exception.CustomException;
import com.example.baam2.model.SessionModel;
import com.example.baam2.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository, AttendanceRepository attendanceRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public SessionResponseDTO createSession(SessionCreateDTO request){
        SessionModel sessionModel = new SessionModel();

        sessionModel.setTitle(request.title());
        sessionModel.setOwner(userRepository.findById(request.ownerId()).orElseThrow(() ->
                new CustomException("OWNER_ID_NOT_EXIST","Session owner id does not exist")));

        sessionModel.setActive(true);
        sessionModel.setCreateAt(LocalDateTime.now());

        return mapToDTO(sessionRepository.save(sessionModel));
    }

    @Transactional
    public void deleteSession(Long id){
        if (!(sessionRepository.existsById(id)))
            throw new CustomException("ID_NOT_EXIST","Session id does not exist");

        attendanceRepository.deleteBySessionId(id);

        sessionRepository.deleteById(id);
    }

    @Transactional
    public void updateSessionName(Long id, SessionUpdateDTO request){
        SessionModel sessionModel = sessionRepository.findById(id).orElseThrow(() ->
                new CustomException("ID_NOT_EXIST","Session id does not exist"));
        sessionModel.setTitle(request.title());
    }

    @Transactional
    public void closeSession(Long id){
        SessionModel sessionModel = sessionRepository.findById(id).orElseThrow(() ->
                new CustomException("ID_NOT_EXIST","Session id does not exist"));
        if (!(sessionModel.isActive())) throw new CustomException("SESSION_ALREADY_CLOSE", "This session is already closed");
        sessionModel.setActive(false);
    }

    public List<SessionResponseDTO> getAllSessions(){
        return sessionRepository.findAll().stream().map(sessionModel ->
                new SessionResponseDTO(
                        sessionModel.getId(),
                        sessionModel.getTitle(),
                        sessionModel.getCreateAt()))
                .collect(Collectors.toList());
    }

    public List<Long> getAllaSessions(){
        return sessionRepository.findAllActiveIds();
    }

    private SessionResponseDTO mapToDTO(SessionModel sessionModel) {
        return new SessionResponseDTO(
                sessionModel.getId(),
                sessionModel.getTitle(),
                sessionModel.getCreateAt()
        );
    }
}
