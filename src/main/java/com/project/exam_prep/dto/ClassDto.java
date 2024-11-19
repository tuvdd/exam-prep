package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Class;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClassDto {
    private Integer id;
    private String name;
    private Integer grade;
    public ClassDto (Class aClass) {
        this.id = aClass.getId();
        this.name = aClass.getName();
        this.grade = aClass.getGrade();
    }
}
