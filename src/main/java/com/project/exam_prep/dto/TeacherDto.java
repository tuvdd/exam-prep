package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Class;
import com.project.exam_prep.entity.QuestionSet;
import com.project.exam_prep.entity.Teacher;
import com.project.exam_prep.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDto {
    private Integer id;
    private String teacherCode;
    private String department;
    private String position;
    private String expertiseArea;
    private UserDto userDto;
    private List<QuestionSetDto> questionSetDtos;
    private Set<ClassDto> classDtoSet;

    public TeacherDto(Teacher teacher) {
        if(teacher != null) {
            this.id = teacher.getId();
            this.teacherCode = teacher.getTeacherCode();
            this.department = teacher.getDepartment();
            this.position = teacher.getPosition();
            this.expertiseArea = teacher.getExpertiseArea();
            this.userDto = new UserDto(teacher.getUser());
            this.questionSetDtos = getQuestionSetDtos(teacher.getQuestionSets());
            this.classDtoSet = getClassDtoSet(teacher.getClasses());
        }
    }

    public List<QuestionSetDto> getQuestionSetDtos(List<QuestionSet> questionSets) {
        List<QuestionSetDto> result = new ArrayList<>();
        for(QuestionSet questionSet : questionSets) {
            result.add(new QuestionSetDto(questionSet));
        }
        return result;
    }

    public Set<ClassDto> getClassDtoSet(Set<Class> classes) {
        Set<ClassDto> result = new HashSet<>();
        for(Class aClass: classes) {
            result.add(new ClassDto(aClass));
        }
        return result;
    }
}
