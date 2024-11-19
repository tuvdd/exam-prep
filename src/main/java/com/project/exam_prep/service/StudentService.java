package com.project.exam_prep.service;

import com.project.exam_prep.dto.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudent();
    StudentDto getStudentById(Integer id);
    StudentDto updateStudent(StudentDto studentDto);

    boolean addStudent(StudentDto studentDto);
    boolean addStudents(List<StudentDto> studentDtos);
}
