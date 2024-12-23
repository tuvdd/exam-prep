package com.project.exam_prep.entity;

import com.project.exam_prep.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRespone {
    private String jwt;
    private String error;
    private UserDto userDto;
}
