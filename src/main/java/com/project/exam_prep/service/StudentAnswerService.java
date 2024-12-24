package com.project.exam_prep.service;

import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.dto.StudentAnswerDto;

import java.util.List;

public interface StudentAnswerService {

    boolean checkStudentAnswer(StudentAnswerDto studentAnswerDto);
    Double calculateScore(List<StudentAnswerDto> studentAnswerDtos);
    boolean saveStudentAnswer(List<StudentAnswerDto> studentAnswerDtos);

    List<StudentAnswerDto> getStudentAnswersByQuizIdAndStudentId(int StudentId, int studentId);
}
