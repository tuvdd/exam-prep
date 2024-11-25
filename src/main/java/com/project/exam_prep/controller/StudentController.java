package com.project.exam_prep.controller;

import com.project.exam_prep.dto.StudentDto;
import com.project.exam_prep.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/admin/save")
    public ResponseEntity<?> saveStudent(@RequestBody StudentDto studentDto) {
        boolean result = studentService.addStudent(studentDto);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/admin/save-from-excel")
    public ResponseEntity<?> saveStudent(@RequestBody List<StudentDto> studentDtos) {
        boolean result = studentService.addStudents(studentDtos);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllStudent() {
        List<StudentDto> studentDtos = studentService.getAllStudent();
        return ResponseEntity.ok(studentDtos);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        StudentDto studentDto = studentService.getStudentById(studentId);
        if(studentDto.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(studentDto);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<StudentDto> updateTeacher(@RequestBody StudentDto studentDto) {
        StudentDto updateStudent = studentService.updateStudent(studentDto);
        if(updateStudent == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(updateStudent);
    }
}
