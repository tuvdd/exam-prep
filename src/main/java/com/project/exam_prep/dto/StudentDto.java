package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Class;
import com.project.exam_prep.entity.Quiz;
import com.project.exam_prep.entity.Student;
import com.project.exam_prep.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto {


    private Integer id;
    private String studentCode;
    private UserDto userDto;
    private Set<ClassDto> classDtoSet;
    private Set<QuizDto> quizDtoSet;

    public StudentDto(Student student) {
        if(student != null) {
            this.id = student.getId();
            this.studentCode = student.getStudentCode();
            this.userDto = new UserDto(student.getUser());
            this.classDtoSet = getClassDtoSet(student.getClasses());
            this.quizDtoSet = getQuizDtoSet(student.getQuizzes());
        }
    }

    public Set<ClassDto> getClassDtoSet(Set<Class> classes) {
        Set<ClassDto> result = new HashSet<>();
        for(Class aClass: classes) {
            result.add(new ClassDto(aClass));
        }
        return result;
    }

    public Set<QuizDto> getQuizDtoSet(Set<Quiz> quizzes) {
        Set<QuizDto> result = new HashSet<>();
        for(Quiz quiz: quizzes) {
            result.add(new QuizDto(quiz));
        }
        return result;
    }

    public static Student  covert(StudentDto studentDto) {

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
