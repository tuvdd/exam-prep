package com.project.exam_prep.controller;

import com.project.exam_prep.dto.AdminDto;
import com.project.exam_prep.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/save")
    public ResponseEntity<?> saveAdmin(@RequestBody AdminDto adminDto) {
        boolean result = adminService.addAdmin(adminDto);
        if (result) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
