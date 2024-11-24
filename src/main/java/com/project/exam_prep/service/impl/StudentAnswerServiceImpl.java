package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.StudentAnswerDto;
import com.project.exam_prep.entity.Answer;
import com.project.exam_prep.repo.AnswerRepo;
import com.project.exam_prep.repo.StudentAnswerRepo;
import com.project.exam_prep.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StudentAnswerServiceImpl implements StudentAnswerService {

    @Autowired
    private StudentAnswerRepo studentAnswerRepo;
    @Autowired
    private AnswerRepo answerRepo;
    @Override
    public boolean checkStudentAnswer(StudentAnswerDto studentAnswerDto) {
        Set<String> choosenAnswer = studentAnswerDto.getSelectedAnswers();
        List<Answer> answerList = answerRepo.findCorrectAnswersByQuestionId(studentAnswerDto.getQuestionId());
        if(answerList.size() != choosenAnswer.size()) return false;
        for(Answer answer: answerList) {
            if(!choosenAnswer.contains(answer.getAnswerText())) return false;
        }
        return true;
    }

    @Override
    public Double calculateScore(List<StudentAnswerDto> studentAnswerDtos) {
        int total = studentAnswerDtos.size();
        int correct = 0;

        for (StudentAnswerDto studentAnswerDto : studentAnswerDtos) {
            if (checkStudentAnswer(studentAnswerDto)) correct++;
        }

        return (double) correct / total * 10;
    }

}
