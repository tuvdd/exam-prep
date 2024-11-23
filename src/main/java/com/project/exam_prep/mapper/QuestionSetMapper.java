package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.QuestionSetDto;
import com.project.exam_prep.entity.QuestionSet;
import com.project.exam_prep.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QuestionSetMapper {
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private QuestionMapper questionMapper;

    public QuestionSet convertToEntity(QuestionSetDto questionSetDto) {
        return new QuestionSet(
                questionSetDto.getId(),
                questionSetDto.getTitle(),
                questionSetDto.getSubject(),
                teacherRepo.findById(questionSetDto.getTeacherId()).orElse(null),
                questionSetDto.getQuestions().stream().map(questionMapper::convertToEntity).collect(Collectors.toSet()));
    }

}
