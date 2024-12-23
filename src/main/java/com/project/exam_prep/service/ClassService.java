package com.project.exam_prep.service;

import com.project.exam_prep.dto.DetailClassDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ClassService {
    boolean addClass(DetailClassDto detailClassDto);
    DetailClassDto getClassById(Integer classId);
    List<DetailClassDto> getAllClasses();
    DetailClassDto updateClass(DetailClassDto detailClassDto);
    boolean deleteClassById(Integer classId);
}
