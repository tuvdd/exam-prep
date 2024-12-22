package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Class;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class DetailClassDto {
    private Integer id;
    private String name;
    private Integer grade;
    private Set<SimpleStudentDto> simpleStudentDtos;
    private Set<SimpleTeacherDto> simpleTeacherDtos;

    public DetailClassDto (Class aClass) {
        this.id = aClass.getId();
        this.name = aClass.getName();
        this.grade = aClass.getGrade();
        this.simpleStudentDtos = aClass.getStudents().stream().map(SimpleStudentDto::new).collect(Collectors.toSet());
        this.simpleTeacherDtos = aClass.getTeachers().stream().map(SimpleTeacherDto::new).collect(Collectors.toSet());
    }
}
