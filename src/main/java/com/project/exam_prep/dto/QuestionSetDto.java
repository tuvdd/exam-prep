package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionSet;
import com.project.exam_prep.repo.QuestionRepo;
import com.project.exam_prep.repo.TeacherRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class QuestionSetDto {
    private Integer id;
    private String title;
    private String subject;
    private Integer teacherId;
    private Set<QuestionDto> questions = new HashSet<>();

    public QuestionSetDto(QuestionSet questionSet) {
        if(questionSet != null) {
            this.id = questionSet.getId();
            this.title = questionSet.getTitle();
            this.subject = questionSet.getSubject();
            this.teacherId = questionSet.getTeacher().getId();
            this.questions = getQuestions(questionSet);
        }
    }

    public Set<QuestionDto> getQuestions(QuestionSet questionSet) {
        Set<QuestionDto> result = new HashSet<>();
        Set<Question> set = questionSet.getQuestions();
        for(Question question: set) {
            result.add(new QuestionDto(question));
        }
        return result;
    }

}
