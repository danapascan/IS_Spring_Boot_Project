package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.RegistrationRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    void save (User user);
    boolean checkEmail(String email);

    UserDto registerUser(RegistrationRequest registrationRequest);

    UserDto getLoginUser();

    UserDto getUserById(Integer id);

    UserDto getUserByUsername(String username);
    List<UserDto> getAllUsers();

    UserDto createUser(User user);

    UserDto updateUser(User user);

    void deleteUser(User user);
    User getByUsername(String username);
}
