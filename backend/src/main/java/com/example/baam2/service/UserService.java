package com.example.baam2.service;

import com.example.baam2.dto.request.UserCreateDTO;
import com.example.baam2.dto.request.UserLoginDTO;
import com.example.baam2.dto.request.UserUpdateDTO;
import com.example.baam2.dto.response.UserDTO;
import com.example.baam2.exception.CustomException;
import com.example.baam2.model.SessionModel;
import com.example.baam2.model.UserModel;
import com.example.baam2.repository.AttendanceRepository;
import com.example.baam2.repository.SessionRepository;
import com.example.baam2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final SessionRepository sessionRepository;

    public UserService(UserRepository userRepository, AttendanceRepository attendanceRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.sessionRepository = sessionRepository;
    }

    public UserDTO getUser(Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("ID_NOT_EXIST", "User id does not exist"));
        return mapToDTO(user);
    }

    public UserDTO loginUser(UserLoginDTO userLoginDTO) {
        UserModel user = userRepository.findByEmail(userLoginDTO.email());

        if (user == null) {
            throw new CustomException("EMAIL_DOES_NOT_EXIST", "User with this email does not exist");
        }

        if (!user.getPassword().equals(userLoginDTO.password())) {
            throw new CustomException("WRONG_PASSWORD", "Password does not match");
        }

        return mapToDTO(user);
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByEmail(userCreateDTO.email()))
            throw new CustomException("EMAIL_ALREADY_EXISTS","User with this email is already exists");
        UserModel newUser = new UserModel(
                null,
                userCreateDTO.email(),
                userCreateDTO.password()
        );
        return mapToDTO(userRepository.save(newUser));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new CustomException("ID_NOT_EXIST","User id does not exist");

        attendanceRepository.deleteByUserId(id);

        List<SessionModel> ownedSessions = sessionRepository.findAllByOwner_Id(id);

        for (SessionModel session : ownedSessions) {
            session.setOwner(null);
        }

        sessionRepository.saveAll(ownedSessions);

        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserModel userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("ID_NOT_EXIST", "User id does not exist"));

        UserModel userToUpdate = new UserModel(
                id,
                userEntity.getEmail(),
                userUpdateDTO.password()
        );

        return mapToDTO(userRepository.save(userToUpdate));
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map( user -> mapToDTO(user)).toList();
    }

    private UserDTO mapToDTO(UserModel userModel) {
        return new UserDTO(
                userModel.getId(),
                userModel.getEmail()
        );
    }
}
