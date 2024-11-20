package com.project.exam_prep.service;

import com.project.exam_prep.dto.QuizDto;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    boolean addQuiz(QuizDto quizDto);
    Optional<QuizDto> getQuizById(Integer id);
    List<QuizDto> getAllQuizzes();
    List<QuizDto> getAllQuizzesByStudentId(Integer studentId);
    List<QuizDto> getAllQuizzesByTeacherId(Integer teacherId);
    QuizDto updateQuiz(QuizDto quizDto);
    boolean deleteQuizById(Integer id);
}
