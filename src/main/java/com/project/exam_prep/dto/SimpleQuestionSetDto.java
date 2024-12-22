package com.project.exam_prep.dto;

import com.project.exam_prep.entity.QuestionSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleQuestionSetDto {
    private Integer id;
    private String title;
    private String subject;
    private Integer teacherId;

    public SimpleQuestionSetDto(QuestionSet questionSet) {
        if(questionSet != null) {
            this.id = questionSet.getId();
            this.title = questionSet.getTitle();
            this.subject = questionSet.getSubject();
            this.teacherId = questionSet.getTeacher().getId();
        }
    }
}