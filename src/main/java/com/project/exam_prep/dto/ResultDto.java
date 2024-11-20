package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Result;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.StudentRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ResultDto {
    private Integer id;
    private double score;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer studentId;
    private Integer quizId;

    private static StudentRepo studentRepo;
    private static QuizRepo quizRepo;

    public static void setRepo(StudentRepo studentRepo, QuizRepo quizRepo) {
        ResultDto.studentRepo = studentRepo;
        ResultDto.quizRepo = quizRepo;
    }

    public ResultDto (Result result) {
        this.id = result.getId();
        this.score = result.getScore();
        this.startTime = result.getStartTime();
        this.endTime = result.getEndTime();
        this.studentId = result.getStudent().getId();
        this.quizId = result.getQuiz().getId();
    }

    public static Result convert(ResultDto resultDto) {
        return new Result(
                resultDto.getId(),
                resultDto.getScore(),
                resultDto.getStartTime(),
                resultDto.getEndTime(),
                studentRepo.findById(resultDto.getStudentId()).orElse(null),
                quizRepo.findById(resultDto.getQuizId()).orElse(null));
    }
}