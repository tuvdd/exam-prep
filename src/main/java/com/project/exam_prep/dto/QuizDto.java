package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Quiz;
import com.project.exam_prep.entity.Result;
import com.project.exam_prep.entity.Student;
import com.project.exam_prep.repo.QuestionSetRepo;
import com.project.exam_prep.repo.StudentRepo;
import com.project.exam_prep.repo.TeacherRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class QuizDto {
    private Integer id;
    private String title;
    private Timestamp startTime;
    private Timestamp endTime;
    private String type;
    private String mode;
    private Set<Integer> studentIds = new HashSet<>();
    private Set<ResultDto> results = new HashSet<>();
    private Integer questionSetId;
    private Integer teacherId;

    public QuizDto (Quiz quiz) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.startTime = quiz.getStartTime();
        this.endTime = quiz.getEndTime();
        this.type = quiz.getType();
        this.mode = quiz.getMode();
        this.studentIds = getListStudentIds(quiz);
        this.results = getListResults(quiz);
        this.questionSetId = quiz.getQuestionSet().getId();
        this.teacherId = quiz.getTeacher().getId();
    }

    private Set<ResultDto> getListResults(Quiz quiz) {
        Set<ResultDto> resultDtos = new HashSet<>();
        Set<Result> resultSet = quiz.getResults();
        for (Result result : resultSet) {
            resultDtos.add(new ResultDto(result));
        }
        
        return resultDtos;
    }

    private Set<Integer> getListStudentIds(Quiz quiz) {
        Set<Integer> studentIds = new HashSet<>();
        Set<Student> students = quiz.getStudents();
        for (Student student : students) {
            studentIds.add(student.getId());
        }
        return studentIds;
    }
}
