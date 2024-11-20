package com.project.exam_prep.service;

import com.project.exam_prep.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionService {
    //    add new question, not include the questionSet relationship
    boolean addQuestion(QuestionDto questionDto);
    boolean addQuestions(List<QuestionDto> questionDtos);
    Optional<QuestionDto> getQuestionById(Integer id);
    List<QuestionDto> getAllQuestionsByTeacherId(Integer teacherId);
    List<QuestionDto> getAllQuestions();
    QuestionDto updateQuestion(QuestionDto questionDto);
    boolean deleteQuestionById(Integer id);
}
