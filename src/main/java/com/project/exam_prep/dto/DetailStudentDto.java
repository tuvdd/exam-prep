package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Class;
import com.project.exam_prep.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DetailStudentDto {
    private Integer id;
    private String studentCode;
    private UserDto userDto;
    private ClassDto classDto;

    public DetailStudentDto(Student student) {
        if (student != null) {
            this.id = student.getId();
            this.studentCode = student.getStudentCode();
            this.userDto = new UserDto(student.getUser());
            this.classDto = student.getCurrentClass() != null ? new ClassDto(student.getCurrentClass()) : null;
        }
    }
}
