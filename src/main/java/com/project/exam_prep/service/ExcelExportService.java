package com.project.exam_prep.service;

import com.project.exam_prep.dto.StudentDto;
import com.project.exam_prep.dto.TeacherDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExcelExportService {
    void exportTeachersToExcel(String filePath);
    void exportStudentsToExcel(String filePath);
}
