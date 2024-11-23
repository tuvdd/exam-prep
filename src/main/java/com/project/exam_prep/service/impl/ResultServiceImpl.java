package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.entity.Result;
import com.project.exam_prep.mapper.ResultMappper;
import com.project.exam_prep.repo.ResultRepo;
import com.project.exam_prep.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepo resultRepo;
    @Autowired
    private ResultMappper resultMappper;

    @Override
    public List<ResultDto> getResultByQuiz(int quizId) {
        List<ResultDto> resultDtoList = new ArrayList<>();

        List<Result> resultList = resultRepo.getResultsByQuizId(quizId);
        for(Result result: resultList) {
            resultDtoList.add(new ResultDto(result));
        }

        return resultDtoList;
    }

    @Override
    public ResultDto getResultByQuizAndStudent(int quizId, int studentId) {
        return new ResultDto(resultRepo.getResultByQuizIdAndStudentId(quizId, studentId));
    }

    @Override
    public boolean saveResult(ResultDto resultDto) {
        Result result = resultMappper.convertToEntity(resultDto);
        resultRepo.save(result);
        return true;
    }

    @Override
    public ResultDto updateResult(ResultDto resultDto) {
        if(!resultRepo.existsById(resultDto.getId())) return null;
        Result result = resultMappper.convertToEntity(resultDto);
        resultRepo.save(result);
        return new ResultDto(resultRepo.findById(resultDto.getId()).get());
    }

    @Override
    public boolean deleteResult(int resultId) {
        if(!resultRepo.existsById(resultId)) return false;
        resultRepo.deleteById(resultId);
        return true;
    }
}
