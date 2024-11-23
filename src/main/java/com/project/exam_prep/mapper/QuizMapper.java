package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.QuizDto;
import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.entity.Quiz;
import com.project.exam_prep.entity.Result;
import com.project.exam_prep.entity.Student;
import com.project.exam_prep.repo.QuestionSetRepo;
import com.project.exam_prep.repo.StudentRepo;
import com.project.exam_prep.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuizMapper {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private QuestionSetRepo questionSetRepo;
    @Autowired
    private ResultMappper resultMappper;

    public Quiz convertToEntity(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDto.getId());
        quiz.setTitle(quizDto.getTitle());
        quiz.setStartTime(quizDto.getStartTime());
        quiz.setEndTime(quizDto.getEndTime());
        quiz.setType(quizDto.getType());
        quiz.setMode(quizDto.getMode());

        // Lấy thông tin học sinh từ StudentRepo dựa trên studentIds
        Set<Student> students = quizDto.getStudentIds().stream()
                .map(studentId -> studentRepo.findById(studentId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        quiz.setStudents(students);

        Set<Result> results = quizDto.getResults().stream()
                .map(resultMappper::convertToEntity).collect(Collectors.toSet());
        quiz.setResults(results);

        quiz.setTeacher(teacherRepo.findById(quizDto.getTeacherId()).orElse(null));
        quiz.setQuestionSet(questionSetRepo.findById(quizDto.getQuestionSetId()).orElse(null));
        return quiz;
    }
}
