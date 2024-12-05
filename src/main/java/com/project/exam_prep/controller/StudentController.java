package com.project.exam_prep.controller;

import com.project.exam_prep.dto.QuizDto;
import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.dto.StudentDto;
import com.project.exam_prep.service.QuizService;
import com.project.exam_prep.service.ResultService;
import com.project.exam_prep.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private ResultService resultService;

    //    ===============================STUDENT===============================
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        StudentDto studentDto = studentService.getStudentById(studentId);
        if(studentDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(studentDto);
    }

    //    ===============================QUIZ===============================

    @GetMapping("/quiz/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Integer id) {
        Optional<QuizDto> quiz = quizService.getQuizById(id);
        return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/quiz/all/{studentId}")
    public List<QuizDto> getAllQuizzesByStudentId(@PathVariable Integer studentId) {
        return quizService.getAllQuizzesByStudentId(studentId);
    }

    //    ===============================RESULT===============================

    @PostMapping("/save")
    public ResponseEntity<?> saveResult(@RequestBody ResultDto resultDto) {
        boolean check = resultService.saveResult(resultDto);
        if(check) return new ResponseEntity<>("Result added successfully", HttpStatus.CREATED);
        return  new ResponseEntity<>("Failed to add Result", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("student-quiz")
    public ResponseEntity<?> getResultByQuizAndStudent(@RequestBody Integer quizId,
                                                       @RequestBody Integer studentId) {
        ResultDto resultDto = resultService.getResultByQuizAndStudent(quizId, studentId);
        return ResponseEntity.ok(resultDto);
    }
}
