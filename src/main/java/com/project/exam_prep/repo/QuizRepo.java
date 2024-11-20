package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {

    Quiz getQuizById(Integer id);

    @Query("SELECT q FROM Quiz q JOIN q.students s WHERE s.id = :studentId")
    List<Quiz> findAllByStudentId(@Param("studentId") Integer studentId);

    List<Quiz> findAllByTeacherId(Integer teacherId);
}
