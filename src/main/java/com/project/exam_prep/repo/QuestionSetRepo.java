package com.project.exam_prep.repo;

import com.project.exam_prep.dto.QuestionSetDto;
import com.project.exam_prep.entity.QuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionSetRepo extends JpaRepository<QuestionSet, Integer> {
    @Query("select new com.project.exam_prep.dto.QuestionSetDto(entity) from QuestionSet as entity")
    List<QuestionSet> getAllQuestionSet();
    @Query("select new com.project.exam_prep.dto.QuestionSetDto(entity) from QuestionSet entity where entity.teacher.id = ?1")
    List<QuestionSet> getAllQuestionSetByTeacherId(Integer teacherId);

    @Query("select new com.project.exam_prep.dto.QuestionSetDto(entity) from QuestionSet entity where entity.id = ?1")
    QuestionSet getQuestionSetById(Integer id);
}
