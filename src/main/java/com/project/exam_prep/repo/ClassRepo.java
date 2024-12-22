package com.project.exam_prep.repo;

import com.project.exam_prep.dto.DetailClassDto;
import com.project.exam_prep.dto.SimpleStudentDto;
import com.project.exam_prep.dto.StudentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.project.exam_prep.entity.Class;


import java.util.List;
import java.util.Set;

@Repository
public interface ClassRepo extends JpaRepository<Class, Integer> {
    @Query("SELECT DISTINCT new com.project.exam_prep.dto.SimpleStudentDto(s) FROM Class c JOIN c.students s JOIN c.teachers t WHERE t.id = :teacherId")
    Set<SimpleStudentDto> findStudentsByTeacherId(@Param("teacherId") Integer teacherId);

}
