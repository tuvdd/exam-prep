package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.StudentAnswerDto;
import com.project.exam_prep.entity.StudentAnswer;
import com.project.exam_prep.repo.QuestionRepo;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentAnswerMapper {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private QuestionRepo questionRepo;

    public StudentAnswer convertToEntity(StudentAnswerDto studentAnswerDto) {
        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setId(studentAnswerDto.getId());
        studentAnswer.setAnswerText(studentAnswerDto.getAnswerText());
        studentAnswer.setSelectedAnswers(studentAnswerDto.getSelectedAnswers());
        studentAnswer.setStudent(studentRepo.getStudentById(studentAnswerDto.getStudentId()));
        studentAnswer.setQuiz(quizRepo.getQuizById(studentAnswerDto.getQuizId()));
        studentAnswer.setQuestion(questionRepo.getReferenceById(studentAnswerDto.getQuestionId()));

        return studentAnswer;
    }
}
