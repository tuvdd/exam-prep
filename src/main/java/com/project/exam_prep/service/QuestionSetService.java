package com.project.exam_prep.service;

import com.project.exam_prep.dto.QuestionSetDto;
import com.project.exam_prep.entity.QuestionSet;

import java.util.List;

public interface QuestionSetService {

    boolean addQuestionSet(QuestionSetDto questionSetDto);
    List<QuestionSetDto> getAllQuestionSet();
    List<QuestionSetDto> getAllQuestionSetByTeacherId(Integer teacherId);
    QuestionSetDto updateQuestionSet(QuestionSetDto questionSetDto);
    boolean deleteQuestionSetById(Integer id);
}
