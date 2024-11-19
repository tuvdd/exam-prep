package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    Student getStudentByUserId(Integer id);
    Student getStudentById(Integer id);
}
