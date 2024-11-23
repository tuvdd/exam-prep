package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.StudentDto;
import com.project.exam_prep.entity.Student;
import com.project.exam_prep.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student covertToEntity(StudentDto studentDto) {
        User user = new User(
                studentDto.getUserDto().getId(),
                studentDto.getUserDto().getUsername(),
                studentDto.getUserDto().getPassword(),
                studentDto.getUserDto().getFirstName(),
                studentDto.getUserDto().getLastName(),
                studentDto.getUserDto().getProfilePicture(),
                studentDto.getUserDto().getEmail(),
                studentDto.getUserDto().getPhoneNumber(),
                studentDto.getUserDto().getAddress(),
                studentDto.getUserDto().getRole(),
                studentDto.getUserDto().is_active()
        );
        Student student;
        student = new Student(
                studentDto.getId(),
                studentDto.getStudentCode(),
                user, null, null, null
        );
        return student;
    }
}
