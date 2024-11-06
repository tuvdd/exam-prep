package com.project.exam_prep.service;

import com.project.exam_prep.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUser();
    boolean addUser(UserDto userDto);
    boolean deleteUser(Integer userId);
    UserDto updateUser (UserDto userDto);
    UserDto login(String username, String password);
}
