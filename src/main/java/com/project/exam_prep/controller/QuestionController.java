package com.project.exam_prep.controller;

import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.dto.StudentDto;
import com.project.exam_prep.repo.QuestionRepo;
import com.project.exam_prep.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepo questionRepo;

    @PostMapping("/save")
    public ResponseEntity<?> saveQuestion(@RequestBody QuestionDto questionDto) {
        boolean result = questionService.addQuestion(questionDto);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/save-from-excel")
    public ResponseEntity<?> saveQuestions(@RequestBody List<QuestionDto> questionDtos) {
        boolean result = questionService.addQuestions(questionDtos);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        List<QuestionDto> questionDtos = questionService.getAllQuestions();
        return ResponseEntity.ok(questionDtos);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable int questionId) {
        Optional<QuestionDto> optionalQuestionDto = questionService.getQuestionById(questionId);

        if (optionalQuestionDto.isPresent()) {
            QuestionDto questionDto = optionalQuestionDto.get();
            return ResponseEntity.ok(questionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<QuestionDto>> getAllQuestionsByTeacherId(@PathVariable Integer teacherId) {
        List<QuestionDto> questions = questionService.getAllQuestionsByTeacherId(teacherId);
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/update")
    public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto questionDto) {
        QuestionDto updatedQuestionDto = questionService.updateQuestion(questionDto);

        if (updatedQuestionDto != null) {
            return ResponseEntity.ok(updatedQuestionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Integer questionId) {
        boolean deleted = questionService.deleteQuestionById(questionId);

        if (deleted) {
            return ResponseEntity.ok("Question with id " + questionId + " has been deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
