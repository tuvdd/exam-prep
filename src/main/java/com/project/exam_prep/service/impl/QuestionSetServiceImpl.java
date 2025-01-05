package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.dto.QuestionSetDto;
import com.project.exam_prep.dto.SimpleQuestionSetDto;
import com.project.exam_prep.entity.Question;
import com.project.exam_prep.entity.QuestionSet;
import com.project.exam_prep.entity.Quiz;
import com.project.exam_prep.mapper.QuestionMapper;
import com.project.exam_prep.repo.*;
import com.project.exam_prep.service.QuestionSetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
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
    private ResultRepo resultRepo;
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
    public List<SimpleQuestionSetDto> getAllQuestionSetByTeacherId(Integer teacherId) {
        return questionSetRepo.getAllQuestionSetByTeacherId(teacherId) ;
    }

//    @Override
//    public QuestionSetDto updateQuestionSet(QuestionSetDto questionSetDto) {
//        Optional<QuestionSet> existingQuestionSet = questionSetRepo
//                .findById(questionSetDto.getId());
//        return existingQuestionSet.map(questionSet -> {
//            questionSet.setTitle(questionSetDto.getTitle());
//            questionSet.setSubject(questionSetDto.getSubject());
//            questionSet.getQuestions().clear();
//            questionSet.getQuestions().addAll(questionSetDto.getQuestions().stream()
//                .map(questionDto -> {
//                    if (questionDto.getId() != null) {
//                        Optional<Question> existingQuestion = questionRepo.findById(questionDto.getId());
//                        existingQuestion.ifPresent(question -> {
//                            question.getQuestionSets().add(questionSet);
//                        });
//                        return existingQuestion.orElse(null);
//                    } else {
//                        Question newQuestion = questionMapper.convertToEntity(questionDto);
//                        newQuestion.setQuestionSets(new HashSet<>());
//                        newQuestion.getQuestionSets().add(questionSet);
//                        return newQuestion;
//                    }
//
//                }).collect(Collectors.toSet()));
//            questionSet.getQuestions().removeIf(Objects::isNull);
//            QuestionSet updatedQuestionSet = questionSetRepo.save(questionSet);
//            return new QuestionSetDto(updatedQuestionSet);
//        }).orElse(null);
//
//    }

    @Override
    public QuestionSetDto updateQuestionSet(QuestionSetDto questionSetDto) {
        // Tìm QuestionSet hiện tại trong cơ sở dữ liệu
        Optional<QuestionSet> existingQuestionSet = questionSetRepo.findById(questionSetDto.getId());

        return existingQuestionSet.map(questionSet -> {
            // Kiểm tra xem QuestionSet hiện tại có đang được sử dụng trong bảng Result (hoặc Quiz) không
            boolean isQuestionSetUsedInQuiz = resultRepo.findByQuizQuestionSetId(questionSet.getId()).isPresent();

            if (isQuestionSetUsedInQuiz) {
                // Nếu QuestionSet đang được sử dụng, thực hiện clone QuestionSet cũ
                QuestionSet clonedQuestionSet = cloneQuestionSet(questionSet);

                // Cập nhật QuestionSet mới vào Quiz hiện tại
                updateQuizWithNewQuestionSet(clonedQuestionSet);
            }

            // Tiến hành cập nhật thông tin QuestionSet với thông tin từ questionSetDto
            questionSet.setTitle(questionSetDto.getTitle());
            questionSet.setSubject(questionSetDto.getSubject());

            // Xóa các câu hỏi cũ và thêm câu hỏi mới
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

            // Loại bỏ câu hỏi null (nếu có)
            questionSet.getQuestions().removeIf(Objects::isNull);

            // Lưu QuestionSet đã cập nhật vào cơ sở dữ liệu
            QuestionSet updatedQuestionSet = questionSetRepo.save(questionSet);

            // Trả về DTO của QuestionSet đã cập nhật
            return new QuestionSetDto(updatedQuestionSet);
        }).orElse(null);
    }

    // Hàm clone QuestionSet cũ
    private QuestionSet cloneQuestionSet(QuestionSet originalQuestionSet) {
        QuestionSet clonedQuestionSet = new QuestionSet();
        clonedQuestionSet.setTitle(originalQuestionSet.getTitle());
        clonedQuestionSet.setSubject(originalQuestionSet.getSubject());
        clonedQuestionSet.setQuestions(new HashSet<>(originalQuestionSet.getQuestions()));
        questionSetRepo.save(clonedQuestionSet); // Lưu cloned QuestionSet vào cơ sở dữ liệu
        return clonedQuestionSet;
    }

    // Hàm gán QuestionSet mới vào Quiz hiện tại
    private void updateQuizWithNewQuestionSet(QuestionSet clonedQuestionSet) {
        // Lấy danh sách các quiz hiện tại đã sử dụng questionSet
        List<Quiz> quizzes = quizRepo.findByQuestionSetId(clonedQuestionSet.getId());

        // Gán QuestionSet mới vào quiz hiện tại
        for (Quiz quiz : quizzes) {
            quiz.setQuestionSet(clonedQuestionSet);
            quizRepo.save(quiz); // Cập nhật quiz với QuestionSet mới
        }
    }


    @Override
    public boolean deleteQuestionSetById(Integer id) {
        if(id != null) {
            boolean isQuestionSetUsedInQuiz = resultRepo.findByQuizQuestionSetId(id).isPresent();
            System.out.println("QS in use");
            if (isQuestionSetUsedInQuiz) return false;
            questionSetRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
