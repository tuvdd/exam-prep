package com.project.exam_prep.controller;

import com.project.exam_prep.dto.*;
import com.project.exam_prep.service.*;
import com.project.exam_prep.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionSetService questionSetService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private ResultService resultService;
    @Autowired
    private JwtUtil jwtUtil;

    //    ===============================TEACHER===============================
    @GetMapping("/{teacherId}")
    public ResponseEntity<?> getTeacherById(@PathVariable int teacherId) {
        TeacherDto teacherDto = teacherService.getTeacherById(teacherId);
        if(teacherDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(teacherDto);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getTeacherInfo(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = authorizationHeader.substring(7); //Bearer
        UserDto userDto = userService.getUserInfo(jwtUtil.extractUsername(jwtToken));
        System.out.println(userDto);
        if(userDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(teacherService.getTeacherByUserId(userDto.getId()));
    }

    //    ===============================STUDENT===============================
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        StudentDto studentDto = studentService.getStudentById(studentId);
        if(studentDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("/student/all/{teacherId}")
    public ResponseEntity<?> getStudentByTeacherId(@PathVariable int teacherId) {
        Set<SimpleStudentDto> studentDtos = studentService.getStudentsByTeacherId(teacherId);
        if(studentDtos.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(studentDtos);
    }



    //    ===============================QUESTION===============================
    @PostMapping("/question/save")
    public ResponseEntity<?> saveQuestion(@RequestBody QuestionDto questionDto) {
        boolean result = questionService.addQuestion(questionDto);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/question/save-from-excel")
    public ResponseEntity<?> saveQuestions(@RequestBody List<QuestionDto> questionDtos) {
        boolean result = questionService.addQuestions(questionDtos);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/question/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable int questionId) {
        Optional<QuestionDto> optionalQuestionDto = questionService.getQuestionById(questionId);

        if (optionalQuestionDto.isPresent()) {
            QuestionDto questionDto = optionalQuestionDto.get();
            return ResponseEntity.ok(questionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/question/all/{teacherId}")
    public ResponseEntity<List<QuestionDto>> getAllQuestionsByTeacherId(@PathVariable Integer teacherId) {
        List<QuestionDto> questions = questionService.getAllQuestionsByTeacherId(teacherId);
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/question/update")
    public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto questionDto) {
        QuestionDto updatedQuestionDto = questionService.updateQuestion(questionDto);

        if (updatedQuestionDto != null) {
            return ResponseEntity.ok(updatedQuestionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/question/delete/{questionId}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Integer questionId) {
        boolean deleted = questionService.deleteQuestionById(questionId);

        if (deleted) {
            return ResponseEntity.ok("Question with id " + questionId + " has been deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //    ===============================QUESTION SET===============================

    @PostMapping("/question-set/save")
    public ResponseEntity<String> addQuestionSet(@RequestBody QuestionSetDto questionSetDto) {
        if (questionSetService.addQuestionSet(questionSetDto)) {
            return ResponseEntity.ok("Question set added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add question set");
        }
    }

    @GetMapping("/question-set/{questionSetId}")
    public ResponseEntity<?> getQuestionSetById(@PathVariable Integer questionSetId) {
        Optional<QuestionSetDto> questionSetDto = questionSetService.getQuestionSetById(questionSetId);
        if (questionSetDto.isPresent()) {
            return ResponseEntity.ok().body(questionSetDto);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/question-set/all/{teacherId}")
    public ResponseEntity<?> getQuestionSetsByTeacherId(@PathVariable Integer teacherId) {
        List<SimpleQuestionSetDto> questionSets = questionSetService.getAllQuestionSetByTeacherId(teacherId);
        return ResponseEntity.ok(questionSets);
    }

    @PutMapping("/question-set/update")
    public ResponseEntity<?> updateQuestionSet(@RequestBody QuestionSetDto questionSetDto) {
        QuestionSetDto updatedQuestionSet = questionSetService.updateQuestionSet(questionSetDto);
        if (updatedQuestionSet != null) {
            return ResponseEntity.ok(updatedQuestionSet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/question-set/{id}")
    public ResponseEntity<String> deleteQuestionSetById(@PathVariable Integer id) {
        if (questionSetService.deleteQuestionSetById(id)) {
            return ResponseEntity.ok("Question set deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete question set");
        }
    }

    //    ===============================QUIZ===============================
    @PostMapping("/quiz/save")
    public ResponseEntity<String> addQuiz(@RequestBody QuizDto quizDto) {
        boolean result = quizService.addQuiz(quizDto);
        if (result) {
            return ResponseEntity.ok("Quiz added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add quiz");
        }
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Integer id) {
        Optional<QuizDto> quiz = quizService.getQuizById(id);
        return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/quiz/all/{teacherId}")
    public List<QuizDto> getAllQuizzesByTeacherId(@PathVariable Integer teacherId) {
        return quizService.getAllQuizzesByTeacherId(teacherId);
    }

    @PutMapping("/quiz/update")
    public ResponseEntity<QuizDto> updateQuiz(@RequestBody QuizDto quizDto) {
        QuizDto updatedQuiz = quizService.updateQuiz(quizDto);
        if (updatedQuiz != null) {
            return ResponseEntity.ok(updatedQuiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/quiz/{id}")
    public ResponseEntity<String> deleteQuizById(@PathVariable Integer id) {
        boolean result = quizService.deleteQuizById(id);
        if (result) {
            return ResponseEntity.ok("Quiz deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //    ===============================RESULT===============================
    @GetMapping("/result/get-by-quiz/{quizId}")
    public  ResponseEntity<?> getResultsByQuiz(@PathVariable Integer quizId) {
        List<ResultDto> resultDtoList = resultService.getResultByQuiz(quizId);
        return ResponseEntity.ok(resultDtoList);
    }

    @PutMapping("/result/update")
    public ResponseEntity<?> updateResult(@RequestBody ResultDto resultDto) {
        ResultDto result = resultService.updateResult(resultDto);
        if(result == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/result/{resultId}")
    public ResponseEntity<?> deleteResult(@PathVariable Integer resultId) {
        boolean check = resultService.deleteResult(resultId);
        if(!check) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok("Result delete successfully");
    }
}
