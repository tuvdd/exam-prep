package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.QuizDto;
import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.dto.TeacherDto;
import com.project.exam_prep.entity.QuestionSet;
import com.project.exam_prep.entity.Quiz;
import com.project.exam_prep.repo.QuestionSetRepo;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.StudentRepo;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    QuizRepo quizRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    QuestionSetRepo questionSetRepo;


    @Override
    public boolean addQuiz(QuizDto quizDto) {
        QuizDto.setRepo(studentRepo);
        ResultDto.setRepo(studentRepo, quizRepo);
        Quiz newQuiz = quizRepo.save(QuizDto.convert(quizDto));
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
            quiz.setStudents(
                    quizDto.getStudentIds().stream().map(
                            studentId -> studentRepo.findById(studentId)
                                    .orElse(null)).collect(Collectors.toSet()));
            quiz.setQuestionSet(questionSetRepo.findById(quizDto.getQuestionSetId()).orElse(null));
            quiz.setResults(quizDto.getResults().stream().map(ResultDto::convert).collect(Collectors.toSet()));
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
