package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailTeacherDto {
    private Integer id;
    private String teacherCode;
    private String department;
    private String position;
    private String expertiseArea;
    private UserDto userDto;

    public DetailTeacherDto(Teacher teacher) {
        if (teacher != null) {
            this.id = teacher.getId();
            this.teacherCode = teacher.getTeacherCode();
            this.department = teacher.getDepartment();
            this.position = teacher.getPosition();
            this.expertiseArea = teacher.getExpertiseArea();
            this.userDto = new UserDto(teacher.getUser());
        }
    }
}