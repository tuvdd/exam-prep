package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.DetailClassDto;
import com.project.exam_prep.dto.SimpleStudentDto;
import com.project.exam_prep.dto.SimpleTeacherDto;
import com.project.exam_prep.entity.Class;
import com.project.exam_prep.repo.ClassRepo;
import com.project.exam_prep.repo.StudentRepo;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.service.ClassService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassServiceImpl implements ClassService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private ClassRepo classRepo;

    @Override
    public boolean addClass(DetailClassDto detailClassDto) {
        Class newClass = new Class();
        newClass.setGrade(detailClassDto.getGrade());
        newClass.setName(detailClassDto.getName());
        newClass.setStudents(new HashSet<>());
        if (detailClassDto.getSimpleStudentDtos() != null) {
            for(SimpleStudentDto studentDto: detailClassDto.getSimpleStudentDtos()) {
                if (studentDto.getId() != null) {
                    studentRepo.findById(studentDto.getId()).ifPresent(student -> {
                        newClass.getStudents().add(student);
                        student.setCurrentClass(newClass);
                    });
                }
            }
        }

        newClass.setTeachers(new HashSet<>());
        if (detailClassDto.getSimpleTeacherDtos() != null) {
            for(SimpleTeacherDto teacherDto : detailClassDto.getSimpleTeacherDtos()) {
                if (teacherDto.getId() != null) {
                    teacherRepo.findById(teacherDto.getId()).ifPresent(teacher -> {
                        newClass.getTeachers().add(teacher);
                        teacher.getClasses().add(newClass);
                    });
                }
            }
        }

        Class savedClass = classRepo.save(newClass);
        return true;
    }

    @Override
    public DetailClassDto getClassById(Integer classId) {
        return classRepo.findById(classId).map(DetailClassDto::new).orElse(null);
    }

    @Override
    public List<DetailClassDto> getAllClasses() {
        return classRepo.findAll().stream().map(DetailClassDto::new).toList();
    }

    @Override
    public DetailClassDto updateClass(DetailClassDto detailClassDto) {
        Optional<Class> existingClass = classRepo.findById(detailClassDto.getId());
        return existingClass.map(oldClass -> {
            oldClass.setGrade(detailClassDto.getGrade());
            oldClass.setName(detailClassDto.getName());
            oldClass.getStudents().clear();
            for(SimpleStudentDto studentDto: detailClassDto.getSimpleStudentDtos()) {
                if (studentDto.getId() != null) {
                    studentRepo.findById(studentDto.getId()).ifPresent(student -> {
                        oldClass.getStudents().add(student);
                        student.setCurrentClass(oldClass);
                    });
                }
            }
            oldClass.getTeachers().clear();
            for(SimpleTeacherDto teacherDto : detailClassDto.getSimpleTeacherDtos()) {
                if (teacherDto.getId() != null) {
                    teacherRepo.findById(teacherDto.getId()).ifPresent(teacher -> {
                        oldClass.getTeachers().add(teacher);
                        teacher.getClasses().add(oldClass);
                    });
                }
            }
            Class updatedClass = classRepo.save(oldClass);
            return new DetailClassDto(updatedClass);


        }).orElse(null);
    }

    @Override
    public boolean deleteClassById(Integer classId) {
        if(classId != null) {
            classRepo.deleteById(classId);
            return true;
        }
        return false;

    }
}
