package com.project.exam_prep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto {

    private long totalStudents;
    private long totalTeachers;
    private long totalQuestions;
    private long totalQuestionSets;
    private long totalQuizzes;
    private long totalQuizAttempts;

}
