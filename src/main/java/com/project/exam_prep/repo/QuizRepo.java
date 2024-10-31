package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {

    Quiz getQuizById(Integer id);
}
