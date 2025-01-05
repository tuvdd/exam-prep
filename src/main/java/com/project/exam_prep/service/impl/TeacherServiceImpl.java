package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.DetailTeacherDto;
import com.project.exam_prep.dto.TeacherDto;
import com.project.exam_prep.entity.Teacher;
import com.project.exam_prep.entity.User;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.repo.UserRepo;
import com.project.exam_prep.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
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
    public List<DetailTeacherDto> getAllTeacher() {
        List<DetailTeacherDto> result = new ArrayList<>();
        List<Teacher> teachers = teacherRepo.findAll();
        for(Teacher teacher: teachers) {
            result.add(new DetailTeacherDto(teacher));
        }
        return result;
    }

    @Override
    public TeacherDto getTeacherByUserId(Integer userId) {
        return new TeacherDto(teacherRepo.getTeacherByUserId(userId));
    }

    @Override
    public TeacherDto getTeacherById(Integer teacherId) {
        return new TeacherDto(teacherRepo.getTeacherById(teacherId));
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
        if (teacherDto == null || teacherDto.getUserDto() == null || teacherDto.getUserDto().getUsername() == null || teacherDto.getUserDto().getPassword() == null) {
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
                "ROLE_TEACHER",
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
