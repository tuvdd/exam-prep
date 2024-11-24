package com.project.exam_prep.controller;

import com.project.exam_prep.dto.StudentAnswerDto;
import com.project.exam_prep.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/studentAnswer")
public class StudentAnswerController {

    @Autowired
    private StudentAnswerService studentAnswerService;

    @PostMapping("/calculateScore")
    public ResponseEntity<Double> calculateScore (@RequestBody List<StudentAnswerDto> studentAnswerDtos) {
        Double score = studentAnswerService.calculateScore(studentAnswerDtos);
        return ResponseEntity.ok(score);
    }
}
