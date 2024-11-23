package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.AnswerDto;
import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.dto.QuestionImageDto;
import com.project.exam_prep.entity.Answer;
import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionImage;
import com.project.exam_prep.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapper {
    @Autowired
    private TeacherRepo teacherRepo;

    public Question convertToEntity(QuestionDto questionDto) {
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
