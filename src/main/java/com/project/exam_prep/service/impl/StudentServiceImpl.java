package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.DetailStudentDto;
import com.project.exam_prep.dto.SimpleStudentDto;
import com.project.exam_prep.dto.StudentDto;

import com.project.exam_prep.entity.Class;
import com.project.exam_prep.entity.Student;
import com.project.exam_prep.entity.User;
import com.project.exam_prep.repo.ClassRepo;
import com.project.exam_prep.repo.StudentRepo;

import com.project.exam_prep.repo.UserRepo;
import com.project.exam_prep.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private ClassRepo classRepo;

    @Override
    public List<DetailStudentDto> getAllStudent() {
        List<DetailStudentDto> result = new ArrayList<>();
        List<Student> students = studentRepo.findAll();
        for(Student student : students) {
            result.add(new DetailStudentDto(student));
        }
        return result;
    }

    @Override
    public StudentDto getStudentByUserId(Integer userId) {
        return new StudentDto(studentRepo.getStudentByUserId(userId));
    }

    @Override
    public StudentDto getStudentById(Integer studentId) {
        return new StudentDto(studentRepo.findById(studentId).orElse(null));
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        Student student = studentRepo.getStudentById(studentDto.getId());
        User user = userService.updateUser(studentDto.getUserDto());
        if(user == null) return null;
        student.setStudentCode(studentDto.getStudentCode());
        student.setUser(user);
        student.getClasses().clear();
        student.getClasses().addAll((studentDto.getClassDtoSet()).stream().map(classDto -> {
                    Class existClass = classRepo.findById(classDto.getId()).orElse(null);
                    if (existClass != null) {
                        existClass.getStudents().add(student);
                    }
                    return existClass;
                }
                ).filter(Objects::nonNull).collect(Collectors.toSet()));

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
                "ROLE_STUDENT",
                true
        );

        userRepo.save(user);
        Student student;
        student = new Student(
                studentDto.getId(),
                studentDto.getStudentCode(),
                user, new HashSet<>(), new HashSet<>(), new HashSet<>()
        );
        student.getClasses().addAll((studentDto.getClassDtoSet()).stream().map(classDto -> {
                    Class existClass = classRepo.findById(classDto.getId()).orElse(null);
                    if (existClass != null) {
                        existClass.getStudents().add(student);
                    }
                    return existClass;
                }
        ).filter(Objects::nonNull).collect(Collectors.toSet()));

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

    @Override
    public Set<SimpleStudentDto> getStudentsByTeacherId(int teacherId) {
        return classRepo.findStudentsByTeacherId(teacherId);
    }
}
