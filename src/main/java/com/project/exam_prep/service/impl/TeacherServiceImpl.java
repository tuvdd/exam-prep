package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.TeacherDto;
import com.project.exam_prep.dto.UserDto;
import com.project.exam_prep.entity.Teacher;
import com.project.exam_prep.entity.User;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.repo.UserRepo;
import com.project.exam_prep.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<TeacherDto> getAllTeacher() {
        List<TeacherDto> result = new ArrayList<>();
        List<Teacher> teachers = teacherRepo.findAll();
        for(Teacher teacher: teachers) {
            result.add(new TeacherDto(teacher));
        }
        return result;
    }

    @Override
    public TeacherDto getTeacherById(Integer id) {
        return new TeacherDto(teacherRepo.getTeacherByUserId(id));
    }

    @Override
    public TeacherDto updateTeacher(TeacherDto teacherDto) {
        Teacher teacher = teacherRepo.getTeacherById(teacherDto.getId());
        User user = userService.updateUser(teacherDto.getUserDto());
        if(user == null) return null;
        teacher.setUser(user);
        teacher.setTeacherCode(teacherDto.getTeacherCode());
        teacher.setDepartment(teacherDto.getDepartment());
        teacher.setPosition(teacherDto.getPosition());
        teacher.setExpertiseArea(teacherDto.getExpertiseArea());

        teacherRepo.save(teacher);
        return new TeacherDto(teacherRepo.getTeacherById(teacherDto.getId()));
    }

    @Override
    public boolean addTeacher(TeacherDto teacherDto) {
        if (teacherDto.getUserDto().getUsername() == null || teacherDto.getUserDto().getPassword() == null) {
            return false;
        }
        String encodePassword = encoder.encode(teacherDto.getUserDto().getPassword());
        if(userRepo.existsUserByUsernameAndPassword(teacherDto.getUserDto().getUsername(), encodePassword)) {
            return false;
        }

        User user = new User(
                teacherDto.getUserDto().getId(),
                teacherDto.getUserDto().getUsername(),
                encodePassword,
                teacherDto.getUserDto().getFirstName(),
                teacherDto.getUserDto().getLastName(),
                teacherDto.getUserDto().getProfilePicture(),
                teacherDto.getUserDto().getEmail(),
                teacherDto.getUserDto().getPhoneNumber(),
                teacherDto.getUserDto().getAddress(),
                teacherDto.getUserDto().getRole(),
                true
        );

        userRepo.save(user);

        Teacher teacher;
        teacher = new Teacher(
                teacherDto.getId(),
                teacherDto.getTeacherCode(),
                teacherDto.getDepartment(),
                teacherDto.getPosition(),
                teacherDto.getExpertiseArea(),
                user, null, null
        );


        teacherRepo.save(teacher);
        return true;
    }

    @Override
    public boolean addTeachers(List<TeacherDto> teacherDtos) {
        for(TeacherDto teacherDto: teacherDtos) {
            if(!this.addTeacher(teacherDto)) return false;
        }
        return true;
    }
}
