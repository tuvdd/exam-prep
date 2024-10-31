package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionSet;
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
public class QuestionSetDto {
    private Integer id;
    private String title;
    private String subject;
    private Integer teacherId;
    private List<QuestionDto> questions;
    private Integer quizId;

    public QuestionSetDto(QuestionSet questionSet) {
        if(questionSet != null) {
            this.id = questionSet.getId();
            this.title = questionSet.getTitle();
            this.subject = questionSet.getSubject();
            this.teacherId = questionSet.getTeacher().getId();
            this.questions = getQuestions(questionSet);
            this.quizId = questionSet.getQuiz().getId();
        }
    }

    public List<QuestionDto> getQuestions(QuestionSet questionSet) {
        List<QuestionDto> result = new ArrayList<>();
        Set<Question> set = questionSet.getQuestions();
        for(Question question: set) {
            result.add(new QuestionDto(question));
        }
        return result;
    }
}
