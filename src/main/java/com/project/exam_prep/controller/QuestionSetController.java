package com.project.exam_prep.controller;

import com.project.exam_prep.dto.QuestionSetDto;
import com.project.exam_prep.service.QuestionSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question-set")
public class QuestionSetController {

    @Autowired
    private QuestionSetService questionSetService;

    @PostMapping("/save")
    public ResponseEntity<String> addQuestionSet(@RequestBody QuestionSetDto questionSetDto) {
        if (questionSetService.addQuestionSet(questionSetDto)) {
            return ResponseEntity.ok("Question set added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add question set");
        }
    }

    @GetMapping("/{questionSetId}")
    public ResponseEntity<?> getQuestionSetById(@PathVariable Integer questionSetId) {
        Optional<QuestionSetDto> questionSetDto = questionSetService.getQuestionSetById(questionSetId);
        if (questionSetDto.isPresent()) {
            return ResponseEntity.ok().body(questionSetDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<QuestionSetDto>> getAllQuestionSets() {
        List<QuestionSetDto> questionSets = questionSetService.getAllQuestionSet();
        return ResponseEntity.ok(questionSets);
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<QuestionSetDto>> getQuestionSetsByTeacherId(@PathVariable Integer teacherId) {
        List<QuestionSetDto> questionSets = questionSetService.getAllQuestionSetByTeacherId(teacherId);
        return ResponseEntity.ok(questionSets);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuestionSet(@RequestBody QuestionSetDto questionSetDto) {
        QuestionSetDto updatedQuestionSet = questionSetService.updateQuestionSet(questionSetDto);
        if (updatedQuestionSet != null) {
            return ResponseEntity.ok(updatedQuestionSet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestionSetById(@PathVariable Integer id) {
        if (questionSetService.deleteQuestionSetById(id)) {
            return ResponseEntity.ok("Question set deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete question set");
        }
    }
}