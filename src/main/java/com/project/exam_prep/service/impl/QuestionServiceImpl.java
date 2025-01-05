package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.dto.SimpleQuestionDto;
import com.project.exam_prep.entity.Answer;
import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionImage;
import com.project.exam_prep.mapper.QuestionMapper;
import com.project.exam_prep.repo.QuestionRepo;
import com.project.exam_prep.repo.QuizRepo;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public boolean addQuestion(QuestionDto questionDto) {
        Question newQuestion = questionMapper.convertToEntity(questionDto);
        newQuestion.setQuestionSets(null);
        questionRepo.save(newQuestion);
        return true;
    }

    public boolean addQuestions(List<QuestionDto> questionDtos) {
        for (QuestionDto questionDto : questionDtos) {
            if (!this.addQuestion(questionDto)) return false;
        }
        return true;
    }

    @Override
    public Optional<QuestionDto> getQuestionById(Integer id) {
        Optional<Question> questionOptional = questionRepo.findById(id);
        return questionOptional.map(QuestionDto::new);
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionRepo.getAllQuestions();
    }

    public List<SimpleQuestionDto> getAllQuestionsByTeacherId(Integer teacherId) {
        return questionRepo.findAllByTeacherId(teacherId).stream().map(SimpleQuestionDto::new).toList();
    }
    @Override
    public QuestionDto updateQuestion(QuestionDto questionDto) {
        Optional<Question> existQuestion = questionRepo.findById(questionDto.getId());
        return existQuestion.map(question -> {
            question.setQuestionText(questionDto.getQuestionText());
            question.setQuestionType(questionDto.getQuestionType());
            ArrayList<QuestionImage> updatedQuestionImages = new ArrayList<>(questionDto.getQuestionImages().stream().map(questionImageDto -> new QuestionImage(
                    questionImageDto.getId(),
                    questionImageDto.getName(),
                    questionImageDto.getImageUrl(),
                    question)).toList());
            question.getQuestionImages().clear();
            question.getQuestionImages().addAll(updatedQuestionImages);
            ArrayList<Answer> updatedAnswers = new ArrayList<>(questionDto.getAnswers().stream().map(answerDto -> new Answer(
                    answerDto.getId(),
                    answerDto.getAnswerText(),
                    answerDto.isCorrect(),
                    question)).toList());
            question.getAnswers().clear();
            question.getAnswers().addAll(updatedAnswers);
            Question updatedQuestion = questionRepo.save(question);
            return new QuestionDto(updatedQuestion);
        }).orElse(null);
    }

    @Override
    public boolean deleteQuestionById(Integer id) {
        if (id != null) {
            int numberOfUseInQuiz = quizRepo.findAllByQuestionId(id).size();
            System.out.println(numberOfUseInQuiz);
            if (numberOfUseInQuiz > 0) return false;
            questionRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
