package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {

    Result getResultByQuizIdAndStudentId(Integer quizId, Integer studentId);
    List<Result> getResultsByQuizId(Integer quizId);
}
