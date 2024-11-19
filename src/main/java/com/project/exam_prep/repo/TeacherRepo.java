package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepo  extends JpaRepository<Teacher, Integer> {

    Teacher getTeacherByUserId(Integer id);
    Teacher getTeacherById(Integer id);

}
