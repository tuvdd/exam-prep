package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo  extends JpaRepository<Teacher, Integer> {

    Teacher getTeacherById(Integer id);
}
