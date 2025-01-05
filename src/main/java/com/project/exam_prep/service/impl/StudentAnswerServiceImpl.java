package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.StudentAnswerDto;
import com.project.exam_prep.entity.Answer;
import com.project.exam_prep.entity.StudentAnswer;
import com.project.exam_prep.mapper.StudentAnswerMapper;
import com.project.exam_prep.repo.AnswerRepo;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.StudentAnswerRepo;
import com.project.exam_prep.service.StudentAnswerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class StudentAnswerServiceImpl implements StudentAnswerService {

    @Autowired
    private StudentAnswerRepo studentAnswerRepo;
    @Autowired
    private AnswerRepo answerRepo;
    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private StudentAnswerMapper studentAnswerMapper;
    @Override
    public boolean checkStudentAnswer(StudentAnswerDto studentAnswerDto) {
        Set<String> choosenAnswer = studentAnswerDto.getSelectedAnswers();
        Set<String> answerList = answerRepo.findCorrectAnswersByQuestionId(studentAnswerDto.getQuestionId());
        if(choosenAnswer.isEmpty()) {
            // kieu dien tu
            if(answerList.contains(studentAnswerDto.getAnswerText().toLowerCase())) return true;
            else return false;
        } else {
            // kieu single choice / multiple choice
            if(answerList.equals(choosenAnswer)) return true;
            else return false;

        }



}

    @Override
    public Double calculateScore(List<StudentAnswerDto> studentAnswerDtos) {
        int total = studentAnswerDtos.size();
        int correct = 0;

        for (StudentAnswerDto studentAnswerDto : studentAnswerDtos) {
            StudentAnswer studentAnswer = studentAnswerMapper.convertToEntity(studentAnswerDto);
            studentAnswerRepo.save(studentAnswer);
            if (checkStudentAnswer(studentAnswerDto)) correct++;
        }

        return (double) correct / total * 10;
    }

    @Override
    public boolean saveStudentAnswer(List<StudentAnswerDto> studentAnswerDtos) {
        for (StudentAnswerDto studentAnswerDto : studentAnswerDtos) {
            StudentAnswer studentAnswer = studentAnswerMapper.convertToEntity(studentAnswerDto);
            studentAnswerRepo.save(studentAnswer);
        }
        return true;
    }

    @Override
    public List<StudentAnswerDto> getStudentAnswersByQuizIdAndStudentId(int quizId, int studentId) {
        List<StudentAnswerDto> result = new ArrayList<>();
        List<StudentAnswer> studentAnswers = studentAnswerRepo.findStudentAnswersByQuizIdAndStudentId(quizId, studentId);
        int x = quizRepo.getQuizById(quizId).getQuestionSet().getQuestions().size();
        System.out.println(x);
        System.out.println(studentAnswers.size());
        studentAnswers = studentAnswers.subList(studentAnswers.size() - x, studentAnswers.size());
        for(StudentAnswer studentAnswer: studentAnswers) {
            result.add(new StudentAnswerDto(studentAnswer));
        }

        return result;
    }

}
