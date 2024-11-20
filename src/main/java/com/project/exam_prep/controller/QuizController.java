package com.project.exam_prep.controller;

import com.project.exam_prep.dto.QuizDto;
import com.project.exam_prep.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/save")
    public ResponseEntity<String> addQuiz(@RequestBody QuizDto quizDto) {
        boolean result = quizService.addQuiz(quizDto);
        if (result) {
            return ResponseEntity.ok("Quiz added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add quiz");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Integer id) {
        Optional<QuizDto> quiz = quizService.getQuizById(id);
        return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public List<QuizDto> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/student/{studentId}")
    public List<QuizDto> getAllQuizzesByStudentId(@PathVariable Integer studentId) {
        return quizService.getAllQuizzesByStudentId(studentId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<QuizDto> getAllQuizzesByTeacherId(@PathVariable Integer teacherId) {
        return quizService.getAllQuizzesByTeacherId(teacherId);
    }

    @PutMapping("/update")
    public ResponseEntity<QuizDto> updateQuiz(@RequestBody QuizDto quizDto) {
        QuizDto updatedQuiz = quizService.updateQuiz(quizDto);
        if (updatedQuiz != null) {
            return ResponseEntity.ok(updatedQuiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuizById(@PathVariable Integer id) {
        boolean result = quizService.deleteQuizById(id);
        if (result) {
            return ResponseEntity.ok("Quiz deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}