package com.example.baam2.service;

import com.example.baam2.dto.request.UserCreateDTO;
import com.example.baam2.dto.request.UserLoginDTO;
import com.example.baam2.dto.request.UserUpdateDTO;
import com.example.baam2.dto.response.UserDTO;
import com.example.baam2.exception.CustomException;
import com.example.baam2.model.UserModel;
import com.example.baam2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new CustomException("ID_NOT_EXIST","User id does not exist");
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

    private UserDTO mapToDTO(UserModel userModel) {
        return new UserDTO(
                userModel.getId(),
                userModel.getEmail()
        );
    }
}
