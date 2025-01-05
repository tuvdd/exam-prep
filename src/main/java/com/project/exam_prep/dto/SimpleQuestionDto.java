package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleQuestionDto {
    private Integer id;
    private String questionText;
    private String questionType;

    public SimpleQuestionDto(Question question) {
        this.id = question.getId();
        this.questionText =    question.getQuestionText();
        this.questionType = question.getQuestionType();
    }
}
