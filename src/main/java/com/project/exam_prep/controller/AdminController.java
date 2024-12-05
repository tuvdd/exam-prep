package com.project.exam_prep.controller;

import com.project.exam_prep.dto.*;
import com.project.exam_prep.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionSetService questionSetService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private ResultService resultService;

    //    ===============================USER===============================
    @GetMapping("/user/ban/{userId}")
    public ResponseEntity<?> banUser(@PathVariable int userId) {
        boolean result = userService.banUser(userId);
        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/unban/{userId}")
    public ResponseEntity<?> unBanUser(@PathVariable int userId) {
        boolean result = userService.unBanUser(userId);
        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //    ===============================ADMIN===============================
    @PostMapping("/save")
    public ResponseEntity<?> saveAdmin(@RequestBody AdminDto adminDto) {
        boolean result = adminService.addAdmin(adminDto);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //    ===============================STUDENT===============================
    @PostMapping("/student/save")
    public ResponseEntity<?> saveStudent(@RequestBody StudentDto studentDto) {
        boolean result = studentService.addStudent(studentDto);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/student/save-from-excel")
    public ResponseEntity<?> saveStudent(@RequestBody List<StudentDto> studentDtos) {
        boolean result = studentService.addStudents(studentDtos);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        StudentDto studentDto = studentService.getStudentById(studentId);
        if(studentDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("/student/all")
    public ResponseEntity<?> getAllStudent() {
        List<StudentDto> studentDtos = studentService.getAllStudent();
        return ResponseEntity.ok(studentDtos);
    }

    @PutMapping("/student/update")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto) {
        StudentDto updateStudent = studentService.updateStudent(studentDto);
        if(updateStudent == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(updateStudent);
    }

    //    ===============================TEACHER===============================
    @PostMapping("/teacher/save")
    public ResponseEntity<?> saveTeacher(@RequestBody TeacherDto teacherDto) {
        boolean result = teacherService.addTeacher(teacherDto);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/teacher/save-from-excel")
    public ResponseEntity<?> saveTeacher(@RequestBody List<TeacherDto> teacherDtos) {
        boolean result = teacherService.addTeachers(teacherDtos);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> getTeacherById(@PathVariable int teacherId) {
        TeacherDto teacherDto = teacherService.getTeacherById(teacherId);
        if(teacherDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(teacherDto);
    }

    @GetMapping("/teacher/all")
    public ResponseEntity<?> getAllTeacher() {
        List<TeacherDto> teacherDtos = teacherService.getAllTeacher();
        return ResponseEntity.ok(teacherDtos);
    }

    @PutMapping("/teacher/update")
    public ResponseEntity<TeacherDto> updateTeacher(@RequestBody TeacherDto teacherDto) {
        TeacherDto teacherDtoUpdate = teacherService.updateTeacher(teacherDto);
        if(teacherDtoUpdate == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(teacherDtoUpdate);
    }

    //    ===============================QUESTION===============================
    @GetMapping("/question/all")
    public ResponseEntity<?> getAllQuestions() {
        List<QuestionDto> questionDtos = questionService.getAllQuestions();
        return ResponseEntity.ok(questionDtos);
    }


    //    ===============================QUESTION SET===============================
    @GetMapping("/question-set/all")
    public ResponseEntity<List<QuestionSetDto>> getAllQuestionSets() {
        List<QuestionSetDto> questionSets = questionSetService.getAllQuestionSet();
        return ResponseEntity.ok(questionSets);
    }

    //    ===============================QUIZ===============================
    @GetMapping("/quiz/all")
    public List<QuizDto> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }


}
