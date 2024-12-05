package com.project.exam_prep.service;

import com.project.exam_prep.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    List<TeacherDto> getAllTeacher();
    TeacherDto getTeacherByUserId(Integer userId);
    TeacherDto getTeacherById(Integer teacherId);
    TeacherDto updateTeacher(TeacherDto teacherDto);

    boolean addTeacher(TeacherDto teacherDto);
    boolean addTeachers(List<TeacherDto> teacherDtos);
}
