package com.project.exam_prep.service;

import com.project.exam_prep.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    //    add new question, not include the questionSet relationship
    QuestionDto addQuestion(QuestionDto questionDto);
    List<QuestionDto> getAllQuestions();
    boolean deleteQuestionById(Integer id);
}
