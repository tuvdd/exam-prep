package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.UserDto;
import com.project.exam_prep.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User convertToEntity(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getProfilePicture(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getAddress(),
                userDto.getRole(),
                userDto.is_active()
        );
    }
}
