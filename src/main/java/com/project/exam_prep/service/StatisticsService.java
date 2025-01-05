package com.project.exam_prep.service;

import com.project.exam_prep.dto.StatisticsDto;
import com.project.exam_prep.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private QuestionSetRepo questionSetRepo;

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private ResultRepo resultRepo;

    public StatisticsDto getStatistics() {
        StatisticsDto statistics = new StatisticsDto();
        statistics.setTotalStudents(userRepo.countByRole("ROLE_STUDENT"));
        statistics.setTotalTeachers(userRepo.countByRole("ROLE_TEACHER"));
        statistics.setTotalQuestions(questionRepo.count());
        statistics.setTotalQuestionSets(questionSetRepo.count());
        statistics.setTotalQuizzes(quizRepo.count());
        statistics.setTotalQuizAttempts(resultRepo.count());
        return statistics;
    }
}

