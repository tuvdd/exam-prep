package com.project.exam_prep.repo;

import com.project.exam_prep.dto.QuestionDto;
import com.project.exam_prep.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    @Query("select new com.project.exam_prep.dto.QuestionDto(entity) from Question as entity")
    List<QuestionDto> getAllQuestions();

//    @Query("select new com.project.exam_prep.dto.QuestionDto(entity) from Question as entity")
//    List<QuestionDto> getAllQuestionsByQuestionSetId();
}
