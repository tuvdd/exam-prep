package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Answer;
import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionImage;
import com.project.exam_prep.entity.QuestionSet;
import com.project.exam_prep.repo.TeacherRepo;
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
public class QuestionDto {
    private Integer id;
    private String questionText;
    private String questionType;
    private Set<Integer> questionSetIds = new HashSet<>();
    private List<QuestionImageDto> questionImages = new ArrayList<>();
    private List<AnswerDto> answers = new ArrayList<>();
    private Integer teacherId;

    // Constructor
    public QuestionDto(Question question) {
        if (question != null) {
            this.id = question.getId();
            this.questionText = question.getQuestionText();
            this.questionType = question.getQuestionType();
            this.questionSetIds = getListQuestionSetId(question);
            this.questionImages = getListQuestionImage(question);
            this.answers = getListAnswer(question);
            this.teacherId = question.getTeacher().getId();
        }
    }

    private Set<Integer> getListQuestionSetId(Question question) {
        Set<Integer> result = new HashSet<>();
        Set<QuestionSet> questionSets = question.getQuestionSets();
        for (QuestionSet questionSet : questionSets) {
            result.add(questionSet.getId());
        }
        return result;
    }

    private List<QuestionImageDto> getListQuestionImage(Question question) {
        List<QuestionImageDto> result = new ArrayList<>();
        List<QuestionImage> questionImages = question.getQuestionImages();
        for (QuestionImage questionImage : questionImages) {
            result.add(new QuestionImageDto(questionImage));
        }
        return result;
    }

    private List<AnswerDto> getListAnswer(Question question) {
        List<AnswerDto> result = new ArrayList<>();
        List<Answer> answers = question.getAnswers();
        for (Answer answer : answers) {
            result.add(new AnswerDto(answer));
        }
        return result;
    }
}