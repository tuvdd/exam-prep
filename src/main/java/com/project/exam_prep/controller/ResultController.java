package com.project.exam_prep.controller;


import com.project.exam_prep.dto.ResultDto;
import com.project.exam_prep.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping("/save")
    public ResponseEntity<?> saveResult(@RequestBody ResultDto resultDto) {
        boolean check = resultService.saveResult(resultDto);
        if(check) return new ResponseEntity<>("Result added successfully", HttpStatus.CREATED);
        return  new ResponseEntity<>("Failed to add Result", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/quiz/{quizId}")
    public  ResponseEntity<?> getResultsByQuiz(@PathVariable Integer quizId) {
        List<ResultDto> resultDtoList = resultService.getResultByQuiz(quizId);
        return ResponseEntity.ok(resultDtoList);
    }

    @GetMapping()
    public ResponseEntity<?> getResultByQuizAndStudent(@RequestBody Integer quizId,
                                                       @RequestBody Integer studentId) {
        ResultDto resultDto = resultService.getResultByQuizAndStudent(quizId, studentId);
        return ResponseEntity.ok(resultDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateResult(@RequestBody ResultDto resultDto) {
        ResultDto result = resultService.updateResult(resultDto);
        if(result == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{reultId}")
    public ResponseEntity<?> deleteResult(@PathVariable Integer resultId) {
        boolean check = resultService.deleteResult(resultId);
        if(!check) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok("Result delete successfully");
    }
}
