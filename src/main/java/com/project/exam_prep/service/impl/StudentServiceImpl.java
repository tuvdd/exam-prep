package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.StudentDto;

import com.project.exam_prep.entity.Student;
import com.project.exam_prep.entity.User;
import com.project.exam_prep.repo.StudentRepo;

import com.project.exam_prep.repo.UserRepo;
import com.project.exam_prep.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<StudentDto> getAllStudent() {
        List<StudentDto> result = new ArrayList<>();
        List<Student> teachers = studentRepo.findAll();
        for(Student teacher: teachers) {
            result.add(new StudentDto(teacher));
        }
        return result;
    }

    @Override
    public StudentDto getStudentById(Integer id) {
        return new StudentDto(studentRepo.getStudentByUserId(id));
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        Student student = studentRepo.getStudentById(studentDto.getId());
        User user = userService.updateUser(studentDto.getUserDto());
        if(user == null) return null;
        student.setStudentCode(studentDto.getStudentCode());
        student.setUser(user);

        studentRepo.save(student);
        return new StudentDto(studentRepo.getStudentById(studentDto.getId()));
    }

    @Override
    public boolean addStudent(StudentDto studentDto) {
        if (studentDto.getUserDto().getUsername() == null || studentDto.getUserDto().getPassword() == null) {
            return false;
        }
        String encodePassword = encoder.encode(studentDto.getUserDto().getPassword());
        if(userRepo.existsUserByUsernameAndPassword(studentDto.getUserDto().getUsername(), encodePassword)) {
            return false;
        }

        User user = new User(
                studentDto.getUserDto().getId(),
                studentDto.getUserDto().getUsername(),
                encodePassword,
                studentDto.getUserDto().getFirstName(),
                studentDto.getUserDto().getLastName(),
                studentDto.getUserDto().getProfilePicture(),
                studentDto.getUserDto().getEmail(),
                studentDto.getUserDto().getPhoneNumber(),
                studentDto.getUserDto().getAddress(),
                studentDto.getUserDto().getRole(),
                true
        );

        userRepo.save(user);

        Student student;
        student = new Student(
                studentDto.getId(),
                studentDto.getStudentCode(),
                user, null, null, null
        );
        studentRepo.save(student);
        return true;
    }

    @Override
    public boolean addStudents(List<StudentDto> studentDtos) {
        for(StudentDto studentDto: studentDtos) {
            if(!this.addStudent(studentDto)) return false;
        }
        return true;
    }
}
