package com.project.exam_prep.controller;

import com.project.exam_prep.dto.*;
import com.project.exam_prep.service.*;
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
    @Autowired
    private QuestionSetService questionSetService;
    @Autowired
    private StudentAnswerService studentAnswerService;

    //    ===============================STUDENT===============================
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        StudentDto studentDto = studentService.getStudentById(studentId);
        if(studentDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("/user/{userId}")
    public  ResponseEntity<?> getStudentByUserId(@PathVariable int userId) {
        StudentDto studentDto = studentService.getStudentByUserId(userId);
        if(studentDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        System.out.println(studentDto);
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

    @PostMapping("/result/save")
    public ResponseEntity<?> saveResult(@RequestBody ResultDto resultDto) {
        boolean check = resultService.saveResult(resultDto);
        if(check) return new ResponseEntity<>("Result added successfully", HttpStatus.CREATED);
        return  new ResponseEntity<>("Failed to add Result", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/student-quiz")
    public ResponseEntity<?> getResultByQuizAndStudent(@RequestBody Integer quizId,
                                                       @RequestBody Integer studentId) {
        ResultDto resultDto = resultService.getResultByQuizAndStudent(quizId, studentId);
        return ResponseEntity.ok(resultDto);
    }

    @PutMapping("/update")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto) {
        StudentDto updateStudent = studentService.updateStudent(studentDto);
        if(updateStudent == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(updateStudent);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<StudentDto> changePassword(@RequestBody StudentDto studentDto) {
        StudentDto updateStudent = studentService.changePasseord(studentDto);
        if(updateStudent == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(updateStudent);
    }

    @GetMapping("/question-set/{questionSetId}")
    public ResponseEntity<?> getQuestionSetById(@PathVariable Integer questionSetId) {
        Optional<QuestionSetDto> questionSetDto = questionSetService.getQuestionSetById(questionSetId);
        if (questionSetDto.isPresent()) {
            return ResponseEntity.ok().body(questionSetDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/student-answer/save")
    public ResponseEntity<?> saveStudentAnswer(@RequestBody List<StudentAnswerDto> studentAnswerDtos) {
        boolean check = studentAnswerService.saveStudentAnswer(studentAnswerDtos);
        if(check) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/student-answer/{studentId}/{quizId}")
    public  ResponseEntity<?> getAllStudentAnswer(@PathVariable Integer studentId,
                                                  @PathVariable Integer quizId) {
        List<StudentAnswerDto> studentAnswerDtos = studentAnswerService.getStudentAnswersByQuizIdAndStudentId(quizId, studentId);
        return ResponseEntity.ok(studentAnswerDtos);
    }

}
