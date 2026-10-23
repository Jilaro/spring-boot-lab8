package ru.urabov.lab8.service;

import ru.urabov.lab8.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    boolean existsByEmail(String email);

    List<UserDto> findAllUsers();
}
