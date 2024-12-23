package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.QuizDto;
import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.dto.StudentDto;
import com.project.exam_prep.entity.Quiz;
import com.project.exam_prep.entity.Result;
import com.project.exam_prep.mapper.ResultMappper;
import com.project.exam_prep.repo.ResultRepo;
import com.project.exam_prep.repo.StudentRepo;
import com.project.exam_prep.service.QuizService;
import com.project.exam_prep.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepo resultRepo;
    @Autowired
    private QuizService quizService;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private ResultMappper resultMappper;

    @Override
    public List<ResultDto> getResultByQuiz(int quizId) {
        List<ResultDto> resultDtoList = new ArrayList<>();
        Set<Integer> studentIds = quizService.getQuizById(quizId).map(QuizDto::getStudentIds).orElse(new HashSet<>());
//        System.out.println(studentIds.toString());
        List<Result> resultList = resultRepo.getResultsByQuizId(quizId);
        List<Integer> testedStudentIds = new ArrayList<>();
        for(Result result: resultList) {
            resultDtoList.add(new ResultDto(result));
            testedStudentIds.add(result.getStudent().getId());
        }
        if (!studentIds.isEmpty()) {
            for (Integer studentId : studentIds) {
                if (!testedStudentIds.contains(studentId)) {
                    resultDtoList.add(new ResultDto(
                            null,
                            0,
                            null,
                            null,
                            studentId,
                            quizId));
                }
            }
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
