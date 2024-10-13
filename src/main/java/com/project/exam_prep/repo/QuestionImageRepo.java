package com.project.exam_prep.repo;

import com.project.exam_prep.dto.AnswerDto;
import com.project.exam_prep.dto.QuestionImageDto;
import com.project.exam_prep.entity.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionImageRepo extends JpaRepository<QuestionImage, Integer> {
    // if set "entity.question_id => error
    @Query("select new com.project.exam_prep.dto.QuestionImageDto(entity) from QuestionImage entity where entity.question.id = ?1")
    List<QuestionImageDto> findQuestionImagesByQuestionId(Integer questionId);
}
