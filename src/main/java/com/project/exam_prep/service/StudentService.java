package com.project.exam_prep.service;

import com.project.exam_prep.dto.DetailStudentDto;
import com.project.exam_prep.dto.SimpleStudentDto;
import com.project.exam_prep.dto.StudentDto;

import java.util.List;
import java.util.Set;

public interface StudentService {

    List<DetailStudentDto> getAllStudent();
    StudentDto getStudentByUserId(Integer userId);
    StudentDto getStudentById(Integer studentId);
    StudentDto updateStudent(StudentDto studentDto);

    boolean addStudent(StudentDto studentDto);
    boolean addStudents(List<StudentDto> studentDtos);

    Set<SimpleStudentDto> getStudentsByTeacherId(int teacherId);
}
