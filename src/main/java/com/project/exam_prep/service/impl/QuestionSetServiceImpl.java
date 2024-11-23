package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.dto.QuestionSetDto;
import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionSet;
import com.project.exam_prep.mapper.QuestionMapper;
import com.project.exam_prep.repo.QuestionRepo;
import com.project.exam_prep.repo.QuestionSetRepo;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.service.QuestionSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionSetServiceImpl implements QuestionSetService {

    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private QuestionSetRepo questionSetRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public boolean addQuestionSet(QuestionSetDto questionSetDto) {
        QuestionSet newQuestionSet = new QuestionSet();
//        newQuestionSet.setId(questionSetDto.getId());
        newQuestionSet.setTitle(questionSetDto.getTitle());
        newQuestionSet.setSubject(questionSetDto.getSubject());
        newQuestionSet.setTeacher(teacherRepo.getTeacherById(questionSetDto.getTeacherId()));
        newQuestionSet.setQuestions(new HashSet<>());
        for(QuestionDto questionDto: questionSetDto.getQuestions()) {
            questionDto.setTeacherId(questionSetDto.getTeacherId());
            if (questionDto.getId() != null) {
                questionRepo.findById(questionDto.getId()).ifPresent(question -> {
                    newQuestionSet.getQuestions().add(question);
                    question.getQuestionSets().add(newQuestionSet);
                });
            } else {
                newQuestionSet.getQuestions().add(questionMapper.convertToEntity(questionDto));
            }
        }

        QuestionSet result = questionSetRepo.save(newQuestionSet);
        return true;
    }

    @Override
    public Optional<QuestionSetDto> getQuestionSetById(Integer id) {
        return questionSetRepo.findById(id).map(QuestionSetDto::new);
    }

    @Override
    public List<QuestionSetDto> getAllQuestionSet() {
        return questionSetRepo.getAllQuestionSet();
    }

    @Override
    public List<QuestionSetDto> getAllQuestionSetByTeacherId(Integer teacherId) {
        return questionSetRepo.getAllQuestionSetByTeacherId(teacherId) ;
    }

    @Override
    public QuestionSetDto updateQuestionSet(QuestionSetDto questionSetDto) {
        Optional<QuestionSet> existingQuestionSet = questionSetRepo
                .findById(questionSetDto.getId());
        return existingQuestionSet.map(questionSet -> {
            questionSet.setTitle(questionSetDto.getTitle());
            questionSet.setSubject(questionSetDto.getSubject());
            questionSet.getQuestions().clear();
            questionSet.getQuestions().addAll(questionSetDto.getQuestions().stream()
                .map(questionDto -> {
                    if (questionDto.getId() != null) {
                        Optional<Question> existingQuestion = questionRepo.findById(questionDto.getId());
                        existingQuestion.ifPresent(question -> {
                            question.getQuestionSets().add(questionSet);
                        });
                        return existingQuestion.orElse(null);
                    } else {
                        Question newQuestion = questionMapper.convertToEntity(questionDto);
                        newQuestion.setQuestionSets(new HashSet<>());
                        newQuestion.getQuestionSets().add(questionSet);
                        return newQuestion;
                    }

                }).collect(Collectors.toSet()));
            questionSet.getQuestions().removeIf(Objects::isNull);
            QuestionSet updatedQuestionSet = questionSetRepo.save(questionSet);
            return new QuestionSetDto(updatedQuestionSet);
        }).orElse(null);

    }

    @Override
    public boolean deleteQuestionSetById(Integer id) {
        if(id != null) {
            questionSetRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
