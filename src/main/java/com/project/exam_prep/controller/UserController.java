package com.project.exam_prep.controller;

import com.project.exam_prep.dto.LoginDTO;
import com.project.exam_prep.dto.UserDto;
import com.project.exam_prep.service.AdminService;
import com.project.exam_prep.service.StudentService;
import com.project.exam_prep.service.TeacherService;
import com.project.exam_prep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        UserDto userDto = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        System.out.println(userDto);
        if(userDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(userDto.getRole().equals("STUDENT")) return ResponseEntity.ok(studentService.getStudentById(userDto.getId()));
        if(userDto.getRole().equals("TEACHER")) return ResponseEntity.ok(teacherService.getTeacherById(userDto.getId()));
        return ResponseEntity.ok(adminService.updateLastLogin(userDto.getId()));
    }

    @GetMapping("/ban/{userId}")
    public ResponseEntity<?> banUser(@PathVariable int userId) {
        boolean result = userService.banUser(userId);
        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/unban/{userId}")
    public ResponseEntity<?> unBanUser(@PathVariable int userId) {
        boolean result = userService.unBanUser(userId);
        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
