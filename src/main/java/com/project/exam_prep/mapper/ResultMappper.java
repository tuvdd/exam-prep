package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.dto.StudentDto;
import com.project.exam_prep.entity.Result;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultMappper {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private StudentMapper studentMapper;

    public Result convertToEntity(ResultDto resultDto) {

        return new Result(
                resultDto.getId(),
                resultDto.getScore(),
                resultDto.getStartTime(),
                resultDto.getEndTime(),
                studentMapper.covertToEntity(resultDto.getStudentDto()),
//                studentRepo.findById(resultDto.getStudentId()).orElse(null),
                quizRepo.findById(resultDto.getQuizId()).orElse(null));

    }
}