package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDto {
    private Integer id;
    private String answerText;
    private boolean isCorrect;

    public AnswerDto(Answer answer) {
        if (answer != null) {
            this.id = answer.getId();
            this.answerText = answer.getAnswerText();
            this.isCorrect = answer.isCorrect();
        }
    }
}