package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.AnswerDto;
import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.dto.QuestionImageDto;
import com.project.exam_prep.dto.QuestionSetDto;
import com.project.exam_prep.entity.Answer;
import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionImage;
import com.project.exam_prep.entity.QuestionSet;
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
    private QuestionSetRepo questionSetRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private QuizRepo quizRepo;
    @Override
    public boolean addQuestionSet(QuestionSetDto questionSetDto) {
        QuestionSet newQuestionSet = new QuestionSet();
        newQuestionSet.setId(questionSetDto.getId());
        newQuestionSet.setTitle(questionSetDto.getTitle());
        newQuestionSet.setSubject(questionSetDto.getSubject());
        newQuestionSet.setTeacher(teacherRepo.getTeacherById(questionSetDto.getTeacherId()));

        Set<Question> questionSet = new HashSet<>();
        for(QuestionDto questionDto: questionSetDto.getQuestions()) {
//
//            Question newQuestion = new Question();
//            newQuestion.setId(questionDto.getId());
//            newQuestion.setQuestionText(questionDto.getQuestionText());
//            newQuestion.setQuestionType(questionDto.getQuestionType());
//
//            List<QuestionImage> questionImages = new ArrayList<>();
//            for(QuestionImageDto questionImageDto : questionDto.getQuestionImages()) {
//                QuestionImage questionImage = new QuestionImage(questionImageDto.getId(), questionImageDto.getName(),
//                        questionImageDto.getImageUrl(),newQuestion);
//                questionImages.add(questionImage);
//            }
//            newQuestion.setQuestionImages(questionImages);
//
//            List<Answer> answers = new ArrayList<>();
//            for(AnswerDto answerDto : questionDto.getAnswers()) {
//                Answer answer = new Answer(answerDto.getId(), answerDto.getAnswerText(),
//                        answerDto.isCorrect(),newQuestion);
//                answers.add(answer);
//            }
//            newQuestion.setAnswers(answers);
//            newQuestion.setQuestionSets(null);
            questionSet.add(QuestionDto.convert(questionDto));
        }

        newQuestionSet.setQuestions(questionSet);
        QuestionSet result = questionSetRepo.save(newQuestionSet);
        return true;
    }

    @Override
    public List<QuestionSetDto> getAllQuestionSet() {
        List<QuestionSetDto> result = new ArrayList<>();
        for(QuestionSet questionSet: questionSetRepo.getAllQuestionSet()) {
            result.add(new QuestionSetDto(questionSet));
        }
        return result;
    }

    @Override
    public List<QuestionSetDto> getAllQuestionSetByTeacherId(Integer teacherId) {
        List<QuestionSetDto> result = new ArrayList<>();
        for(QuestionSet questionSet: questionSetRepo.getAllQuestionSetByTeacherId(teacherId)) {
            result.add(new QuestionSetDto(questionSet));
        }
        return result ;
    }

    @Override
    public QuestionSetDto updateQuestionSet(QuestionSetDto questionSetDto) {
        Optional<QuestionSet> existingQuestionSet = questionSetRepo
                .findById(questionSetDto.getId());
        return existingQuestionSet.map(questionSet -> {
            questionSet.setTitle(questionSetDto.getTitle());
            questionSet.setSubject(questionSetDto.getSubject());
            questionSet.setQuestions(questionSetDto.getQuestions().stream().map(QuestionDto::convert).collect(Collectors.toSet()));
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
