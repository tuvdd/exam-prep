package com.project.exam_prep.controller;

import com.project.exam_prep.service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/export")
public class ExcelExportController {
    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/admin/teacher-list")
    public void exportTeachersToExcel(@RequestParam("filePath") String filepath) {
        if (filepath.isEmpty()) filepath = "Teachers.xlsx";
        excelExportService.exportTeachersToExcel(filepath);
    }

    @GetMapping("/admin/student-list")
    public void exportStudentsToExcel(@RequestParam("filePath") String filepath) {
        if (filepath.isEmpty()) filepath = "Student.xlsx";
        excelExportService.exportStudentsToExcel(filepath);
    }
}
