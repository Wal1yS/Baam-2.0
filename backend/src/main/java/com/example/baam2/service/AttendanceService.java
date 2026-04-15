package com.example.baam2.service;

import com.example.baam2.dto.request.AttendanceCreateDTO;
import com.example.baam2.dto.request.AttendanceGpsCreateDTO;
import com.example.baam2.dto.response.AttendanceResponseDTO;
import com.example.baam2.dto.response.UserAttendanceDTO;
import com.example.baam2.model.AttendanceModel;
import com.example.baam2.model.SessionModel;
import com.example.baam2.repository.AttendanceRepository;
import com.example.baam2.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.baam2.exception.CustomException;
import com.example.baam2.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(SessionRepository sessionRepository, UserRepository userRepository, AttendanceRepository attendanceRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
    }



    public AttendanceResponseDTO createAttendance(AttendanceCreateDTO request){
        if (attendanceRepository.existsByUserIdAndSessionId(request.userId(), request.sessionId()))
            throw new CustomException("USER_ALREADY_ATTENDED_SESSION", "User with this this id has already attended session with this id");

        AttendanceModel attendanceModel = new AttendanceModel();

        attendanceModel.setUser(userRepository.findById(request.userId()).orElseThrow( () -> new CustomException("USER_ID_NOT_EXIST","User id does not exist")));
        var session = sessionRepository.findById(request.sessionId()).orElseThrow(() -> new CustomException("SESSION_ID_NOT_EXIST","SESSION id does not exist"));
        attendanceModel.setSession(session);
        if (!(attendanceModel.getSession().isActive())) throw new CustomException("SESSION_IS_CLOSED", "Session is already closed");
        attendanceModel.setTimestamp(LocalDateTime.now());
        return mapToDTO(attendanceRepository.save(attendanceModel));
    }

    public AttendanceResponseDTO createGpsAttendance(AttendanceGpsCreateDTO request){
        SessionModel session = sessionRepository.findById(request.sessionId()).orElseThrow(()
                -> new CustomException("SESSION_ID_NOT_EXISTS", "Session with this id does not exist"));
        boolean inClass = isInClass(session.getLatitude(), session.getLongitude(), session.getAllowedRadius(),
                request.latitude(), request.longitude());
        if (!inClass)
            throw new CustomException("OUT_OF_ATTENDANCE_RADIUS", "User is out of attendance radius");

        AttendanceCreateDTO basicCreationDTO = new AttendanceCreateDTO(
                request.sessionId(),
                request.userId()
        );

        return createAttendance(basicCreationDTO);
    }


    public void deleteAttendance(Long id){
        if (!(attendanceRepository.existsById(id)))
            throw new CustomException("ID_NOT_EXIST","Attendance id does not exist");
        attendanceRepository.deleteById(id);
    }

    public List<UserAttendanceDTO> getAllUserAttendance(Long id){
        return attendanceRepository.findAllByUserId(id).stream().map(attendanceModel -> new UserAttendanceDTO(
                        attendanceModel.getId(),
                        attendanceModel.getSession().getTitle(),
                        attendanceModel.getSession().getOwner().getEmail(),
                        attendanceModel.getTimestamp()))
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDTO> getAllAttendance(){
        return attendanceRepository.findAll().stream().map(attendanceModel -> new AttendanceResponseDTO(
                        attendanceModel.getId(),
                        attendanceModel.getTimestamp()))
                .collect(Collectors.toList());
    }

    private AttendanceResponseDTO mapToDTO(AttendanceModel attendanceModel) {
        return new AttendanceResponseDTO(
                attendanceModel.getId(),
                attendanceModel.getTimestamp()
        );
    }

    private boolean isInClass(Double originalLat, Double originalLong, Double allowedRadius, Double studentLat, Double studentLong) {
        final int EARTH_RADIUS_METERS = 6371000;

        double dLat = Math.toRadians(originalLat - studentLat);
        double dLon = Math.toRadians(originalLong - studentLong);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(studentLat)) * Math.cos(Math.toRadians(originalLat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_METERS * c;

        return distance <= allowedRadius;
    }

}