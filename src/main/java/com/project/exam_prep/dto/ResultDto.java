package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Result;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.StudentRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private Integer id;
    private double score;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer studentId;
    private Integer quizId;
    //private StudentDto studentDto;

    public ResultDto (Result result) {
        this.id = result.getId();
        this.score = result.getScore();
        this.startTime = result.getStartTime();
        this.endTime = result.getEndTime();
        this.studentId = result.getStudent().getId();
        this.quizId = result.getQuiz().getId();
        //this.studentDto = new StudentDto(result.getStudent());
    }
}
