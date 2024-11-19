package com.project.exam_prep.controller;

import com.project.exam_prep.dto.TeacherDto;
import com.project.exam_prep.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTeacher(@RequestBody TeacherDto teacherDto) {
        boolean result = teacherService.addTeacher(teacherDto);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/save-from-excel")
    public ResponseEntity<?> saveTeacher(@RequestBody List<TeacherDto> teacherDtos) {
        boolean result = teacherService.addTeachers(teacherDtos);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAllTeacher() {
        List<TeacherDto> teacherDtos = teacherService.getAllTeacher();
        return ResponseEntity.ok(teacherDtos);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<?> getTeacherById(@PathVariable int teacherId) {
        TeacherDto teacherDto = teacherService.getTeacherById(teacherId);
        if(teacherDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(teacherDto);
    }

    @PutMapping("/update")
    public ResponseEntity<TeacherDto> updateTeacher(@RequestBody TeacherDto teacherDto) {
        TeacherDto teacherDtoUpdate = teacherService.updateTeacher(teacherDto);
        if(teacherDtoUpdate == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(teacherDtoUpdate);
    }
}
