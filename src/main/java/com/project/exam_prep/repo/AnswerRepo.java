package com.project.exam_prep.repo;

import com.project.exam_prep.dto.AnswerDto;
import com.project.exam_prep.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Integer> {
    // if set "entity.question_id => error
    @Query("select new com.project.exam_prep.dto.AnswerDto(entity) from Answer entity where entity.question.id = ?1")
    List<AnswerDto> findAnswersByQuestionId(Integer questionId);

    @Query("SELECT entity.answerText FROM Answer entity " +
            "WHERE entity.question.id = ?1 AND entity.isCorrect = true")
    Set<String> findCorrectAnswersByQuestionId(Integer questionId);

}
