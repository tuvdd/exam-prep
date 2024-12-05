package com.project.exam_prep.controller;

import com.project.exam_prep.dto.StudentAnswerDto;
import com.project.exam_prep.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/result")
public class ResultController {

    @Autowired
    private StudentAnswerService studentAnswerService;

    @PostMapping("/calculate-score")
    public ResponseEntity<Double> calculateScore (@RequestBody List<StudentAnswerDto> studentAnswerDtos) {
        Double score = studentAnswerService.calculateScore(studentAnswerDtos);
        return ResponseEntity.ok(score);
    }
}
