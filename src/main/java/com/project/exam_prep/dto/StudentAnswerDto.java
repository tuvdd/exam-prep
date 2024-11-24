package com.project.exam_prep.dto;

import com.project.exam_prep.entity.StudentAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentAnswerDto {
    private Integer id;
    private String answerText;
    private Set<String> selectedAnswers;
    private Integer studentId;
    private Integer questionId;
    private Integer quizId;

    public StudentAnswerDto (StudentAnswer studentAnswer) {
        this.id = studentAnswer.getId();
        this.answerText = studentAnswer.getAnswerText();
        this.selectedAnswers = studentAnswer.getSelectedAnswers();
        this.studentId = studentAnswer.getStudent().getId();
        this.questionId = studentAnswer.getQuestion().getId();
        this.quizId = studentAnswer.getQuiz().getId();
    }
}
