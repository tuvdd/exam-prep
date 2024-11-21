package com.project.exam_prep.service;

import com.project.exam_prep.dto.ResultDto;

import java.util.List;

public interface ResultService {

    List<ResultDto> getResultByQuiz(int quizId);
    ResultDto getResultByQuizAndStudent(int quizId, int studentId);
    boolean saveResult(ResultDto resultDto);
    ResultDto updateResult(ResultDto resultDto);
    boolean deleteResult(int resultId);
}
