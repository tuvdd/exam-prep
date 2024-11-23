package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.TeacherDto;
import com.project.exam_prep.entity.Teacher;
import com.project.exam_prep.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public Teacher covertToEntity(TeacherDto teacherDto) {
        User user = new User(
                teacherDto.getUserDto().getId(),
                teacherDto.getUserDto().getUsername(),
                teacherDto.getUserDto().getPassword(),
                teacherDto.getUserDto().getFirstName(),
                teacherDto.getUserDto().getLastName(),
                teacherDto.getUserDto().getProfilePicture(),
                teacherDto.getUserDto().getEmail(),
                teacherDto.getUserDto().getPhoneNumber(),
                teacherDto.getUserDto().getAddress(),
                teacherDto.getUserDto().getRole(),
                teacherDto.getUserDto().is_active()
        );

        Teacher teacher;
        teacher = new Teacher(
                teacherDto.getId(),
                teacherDto.getTeacherCode(),
                teacherDto.getDepartment(),
                teacherDto.getPosition(),
                teacherDto.getExpertiseArea(),
                user, null, null
        );

        return teacher;
    }

}
