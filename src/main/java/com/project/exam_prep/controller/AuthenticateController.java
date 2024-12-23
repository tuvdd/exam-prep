package com.project.exam_prep.controller;

import com.project.exam_prep.dto.LoginDto;
import com.project.exam_prep.dto.UserDto;
import com.project.exam_prep.entity.AuthenticationRespone;
import com.project.exam_prep.service.AdminService;
import com.project.exam_prep.service.StudentService;
import com.project.exam_prep.service.TeacherService;
import com.project.exam_prep.service.UserService;
import com.project.exam_prep.service.impl.CustomUserDetailsServiceImpl;
import com.project.exam_prep.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new AuthenticationRespone(jwt, null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationRespone(null, "Login Failed! " + e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDTO) {
        // Xác thực người dùng
        UserDto userDto = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        if (userDto == null) {
            // Nếu không tìm thấy người dùng, trả về lỗi 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Tạo token JWT
        try {
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDTO.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Trả về thông tin người dùng và token
            AuthenticationRespone response = new AuthenticationRespone(jwt,null, userDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Nếu có lỗi khi tạo token, trả về lỗi 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationRespone(null, "Error generating token: " + e.getMessage(), null));
        }
    }

}
