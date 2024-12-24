package com.project.exam_prep.repo;

import com.project.exam_prep.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnswerRepo extends JpaRepository<StudentAnswer, Integer> {

    List<StudentAnswer> findStudentAnswersByQuizIdAndStudentId(int quizId, int studentId);
}
