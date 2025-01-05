package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SimpleStudentDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String studentCode;
    private ClassDto classDto;

    public SimpleStudentDto(Student student) {
        this.id = student.getId();
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.studentCode = student.getStudentCode();
        this.classDto = new ClassDto(student.getCurrentClass());
    }
}
