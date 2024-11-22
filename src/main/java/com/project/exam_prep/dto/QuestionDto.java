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

    private static TeacherRepo teacherRepo;

    public static void setRepo(TeacherRepo teacherRepo){
        QuestionDto.teacherRepo = teacherRepo;
    }

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

    public static Question convert(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setQuestionText(questionDto.getQuestionText());
        question.setQuestionType(questionDto.getQuestionType());

        List<QuestionImage> questionImages = new ArrayList<>();
        for(QuestionImageDto questionImageDto : questionDto.getQuestionImages()) {
            QuestionImage questionImage = new QuestionImage(questionImageDto.getId(), questionImageDto.getName(),
                    questionImageDto.getImageUrl(), question);
            questionImages.add(questionImage);
        }
        question.setQuestionImages(questionImages);

        List<Answer> answers = new ArrayList<>();
        for(AnswerDto answerDto : questionDto.getAnswers()) {
            Answer answer = new Answer(answerDto.getId(), answerDto.getAnswerText(),
                    answerDto.isCorrect(), question);
            answers.add(answer);
        }
        question.setAnswers(answers);
        question.setTeacher(teacherRepo.findById(questionDto.getTeacherId()).orElse(null));
        return question;
    }
}