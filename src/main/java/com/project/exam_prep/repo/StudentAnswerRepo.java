package com.project.exam_prep.repo;

import com.project.exam_prep.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAnswerRepo extends JpaRepository<StudentAnswer, Integer> {
}
