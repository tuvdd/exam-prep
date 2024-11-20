package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.AnswerDto;
import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.dto.QuestionImageDto;
import com.project.exam_prep.entity.Answer;
import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionImage;
import com.project.exam_prep.repo.QuestionRepo;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Override
    public boolean addQuestion(QuestionDto questionDto) {
        Question newQuestion = QuestionDto.convert(questionDto);
        newQuestion.setQuestionSets(null);
        questionRepo.save(newQuestion);
        return true;
    }

    public boolean addQuestions(List<QuestionDto> questionDtos) {
        for (QuestionDto questionDto : questionDtos) {
            if (!this.addQuestion(questionDto)) return false;
        }
        return true;
    }

    @Override
    public Optional<QuestionDto> getQuestionById(Integer id) {
        Optional<Question> questionOptional = questionRepo.findById(id);
        return questionOptional.map(QuestionDto::new);
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionRepo.getAllQuestions();
    }

    public List<QuestionDto> getAllQuestionsByTeacherId(Integer teacherId) {
        return questionRepo.findAllByTeacherId(teacherId).stream().map(QuestionDto::new).toList();
    }
    @Override
    public QuestionDto updateQuestion(QuestionDto questionDto) {
        Optional<Question> existQuestion = questionRepo.findById(questionDto.getId());
        return existQuestion.map(question -> {
                question.setQuestionText(questionDto.getQuestionText());
                question.setQuestionType(question.getQuestionType());
                question.setQuestionImages(questionDto.getQuestionImages().stream().map(questionImageDto -> new QuestionImage(
                        questionImageDto.getId(),
                        questionImageDto.getName(),
                        questionImageDto.getImageUrl(),
                        question)).toList());
                question.setAnswers(questionDto.getAnswers().stream().map(answerDto -> new Answer(
                        answerDto.getId(),
                        answerDto.getAnswerText(),
                        answerDto.isCorrect(),
                        question
                )).toList());
                Question newQuestion = questionRepo.save(question);
                return new QuestionDto(newQuestion);
        }).orElse(null);
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
