package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.QuizDto;
import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.dto.TeacherDto;
import com.project.exam_prep.entity.QuestionSet;
import com.project.exam_prep.entity.Quiz;
import com.project.exam_prep.entity.Result;
import com.project.exam_prep.entity.Student;
import com.project.exam_prep.mapper.QuizMapper;
import com.project.exam_prep.mapper.ResultMappper;
import com.project.exam_prep.repo.QuestionSetRepo;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.StudentRepo;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.service.QuizService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private QuestionSetRepo questionSetRepo;
    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    private ResultMappper resultMappper;


    @Override
    public boolean addQuiz(QuizDto quizDto) {
        Quiz newQuiz = quizMapper.convertToEntity(quizDto);
        for (Student student : newQuiz.getStudents()) {
            student.getQuizzes().add(newQuiz);
        }
        for (Result result : newQuiz.getResults()) {
            result.setQuiz(newQuiz);
        }
//        newQuiz.getTeacher().set
        Quiz savedQuiz = quizRepo.save(newQuiz);
        return true;
    }

    @Override
    public Optional<QuizDto> getQuizById(Integer id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        return quiz.map(QuizDto::new);
    }

    @Override
    public List<QuizDto> getAllQuizzes() {
        return quizRepo.findAll().stream().map(QuizDto::new).toList();
    }

    @Override
    public List<QuizDto> getAllQuizzesByStudentId(Integer studentId) {
        return quizRepo.findAllByStudentId(studentId).stream().map(QuizDto::new).toList();
    }

    @Override
    public List<QuizDto> getAllQuizzesByTeacherId(Integer teacherId) {
        return quizRepo.findAllByTeacherId(teacherId).stream().map(QuizDto::new).toList();
    }

    @Override
    public QuizDto updateQuiz(QuizDto quizDto) {
        Optional<Quiz> existingQuiz = quizRepo.findById(quizDto.getId());
        return existingQuiz.map(quiz -> {
            quiz.setTitle(quizDto.getTitle());
            quiz.setStartTime(quizDto.getStartTime());
            quiz.setEndTime(quizDto.getEndTime());
            quiz.setMode(quizDto.getMode());
            quiz.setType(quizDto.getType());
//            quiz.setTeacher(teacherRepo.findById(quizDto.getTeacherId()).orElse(null));
            quiz.getStudents().clear();
            quiz.getStudents().addAll(
                    quizDto.getStudentIds().stream().map(
                            studentId -> studentRepo.findById(studentId)
                                    .orElse(null)).collect(Collectors.toSet()));
            quiz.setQuestionSet(questionSetRepo.findById(quizDto.getQuestionSetId()).orElse(null));
            quiz.getResults().clear();
            quiz.getResults().addAll(quizDto.getResults().stream().map(resultMappper::convertToEntity).collect(Collectors.toSet()));
            Quiz updatedQuiz = quizRepo.save(quiz);
            return new QuizDto(updatedQuiz);
        }).orElse(null);
    }

    @Override
    public boolean deleteQuizById(Integer id) {
        if (id != null) {
            quizRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
