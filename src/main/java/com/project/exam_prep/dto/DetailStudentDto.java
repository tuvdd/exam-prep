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
    private Set<ClassDto> classDtoSet;

    public DetailStudentDto(Student student) {
        if (student != null) {
            this.id = student.getId();
            this.studentCode = student.getStudentCode();
            this.userDto = new UserDto(student.getUser());
            this.classDtoSet = getClassDtoSet(student.getClasses());
        }
    }

    public Set<ClassDto> getClassDtoSet(Set<Class> classes) {
        Set<ClassDto> result = new HashSet<>();
        for (Class aClass : classes) {
            result.add(new ClassDto(aClass));
        }
        return result;
    }
}
