package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {

    Result getResultByQuizIdAndStudentId(Integer quizId, Integer studentId);
    List<Result> getResultsByQuizId(Integer quizId);

    // Kiểm tra nếu có Result nào với questionSetId nhất định
    @Query("SELECT r FROM Result r WHERE r.quiz.questionSet.id = :questionSetId")
    Optional<Result> findByQuizQuestionSetId(Integer questionSetId);
}
