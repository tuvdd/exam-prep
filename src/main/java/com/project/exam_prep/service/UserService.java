package com.project.exam_prep.service;

import com.project.exam_prep.dto.UserDto;
import com.project.exam_prep.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUser();
    boolean addUser(UserDto userDto);
    boolean banUser(Integer userId);
    boolean unBanUser(Integer userId);
    User updateUser (UserDto userDto);
    UserDto login(String username, String password);
    UserDto getUserInfo(String username);
}
