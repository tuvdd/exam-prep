package com.project.exam_prep.service;

import com.project.exam_prep.dto.QuestionSetDto;

import java.util.List;

public interface QuestionSetService {

    QuestionSetDto addQuestionSet(QuestionSetDto questionSetDto);
    List<QuestionSetDto> getAllQuestionSet();
    List<QuestionSetDto> getAllQuestionSetByTeacherId(Integer teacherId);
    boolean deleteQuestionSetById(Integer id);
}
