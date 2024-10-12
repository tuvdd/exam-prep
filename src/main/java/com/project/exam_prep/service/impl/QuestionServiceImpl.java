package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.AnswerDto;
import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.dto.QuestionImageDto;
import com.project.exam_prep.entity.Answer;
import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionImage;
import com.project.exam_prep.repo.QuestionRepo;
import com.project.exam_prep.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Override
    public QuestionDto addQuestion(QuestionDto questionDto) {
        Question newQuestion = new Question();
        newQuestion.setId(questionDto.getId());
        newQuestion.setQuestionText(questionDto.getQuestionText());
        newQuestion.setQuestionType(questionDto.getQuestionType());

        List<QuestionImage> questionImages = new ArrayList<>();
        for(QuestionImageDto questionImageDto : questionDto.getQuestionImages()) {
            QuestionImage questionImage = new QuestionImage(questionImageDto.getId(), questionImageDto.getName(),
                    questionImageDto.getImageUrl(),newQuestion);
            questionImages.add(questionImage);
        }
        newQuestion.setQuestionImages(questionImages);

        List<Answer> answers = new ArrayList<>();
        for(AnswerDto answerDto : questionDto.getAnswers()) {
            Answer answer = new Answer(answerDto.getId(), answerDto.getAnswerText(),
                    answerDto.isCorrect(),newQuestion);
            answers.add(answer);
        }
        newQuestion.setAnswers(answers);
        newQuestion.setQuestionSets(null);
        return new QuestionDto(questionRepo.save(newQuestion));
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionRepo.getAllQuestions();
    }

    @Override
    public boolean deleteQuestionById(Integer id) {
        if (id != null) {
            questionRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
