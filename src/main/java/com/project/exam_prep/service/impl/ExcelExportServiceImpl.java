package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.StudentDto;
import com.project.exam_prep.dto.TeacherDto;
import com.project.exam_prep.repo.StudentRepo;
import com.project.exam_prep.repo.TeacherRepo;
import com.project.exam_prep.service.ExcelExportService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Override
    public void exportTeachersToExcel(String filePath) {
        List<TeacherDto> teacherDtos = teacherRepo.findAll().stream().map(TeacherDto::new).toList();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Teachers");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Index", "Username", "First Name", "Last Name", "Email", "Phone Number", "Address", "Active",
                    "Teacher Code", "Department", "Position", "Expertise Area"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (TeacherDto teacherDto : teacherDtos) {
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(rowNum++);
                row.createCell(1).setCellValue(teacherDto.getUserDto().getUsername());
                row.createCell(2).setCellValue(teacherDto.getUserDto().getFirstName());
                row.createCell(3).setCellValue(teacherDto.getUserDto().getLastName());
                row.createCell(4).setCellValue(teacherDto.getUserDto().getEmail());
                row.createCell(5).setCellValue(teacherDto.getUserDto().getPhoneNumber());
                row.createCell(6).setCellValue(teacherDto.getUserDto().getAddress());
                row.createCell(7).setCellValue(teacherDto.getUserDto().is_active());
                row.createCell(8).setCellValue(teacherDto.getTeacherCode());
                row.createCell(9).setCellValue(teacherDto.getDepartment());
                row.createCell(10).setCellValue(teacherDto.getPosition());
                row.createCell(11).setCellValue(teacherDto.getExpertiseArea());
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exportStudentsToExcel(String filePath) {
        List<StudentDto> studentDtos = studentRepo.findAll().stream().map(StudentDto::new).toList();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Index", "Username", "First Name", "Last Name", "Email", "Phone Number", "Address", "Active",
                    "Student Code"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (StudentDto studentDto : studentDtos) {
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(rowNum++);
                row.createCell(1).setCellValue(studentDto.getUserDto().getUsername());
                row.createCell(2).setCellValue(studentDto.getUserDto().getFirstName());
                row.createCell(3).setCellValue(studentDto.getUserDto().getLastName());
                row.createCell(4).setCellValue(studentDto.getUserDto().getEmail());
                row.createCell(5).setCellValue(studentDto.getUserDto().getPhoneNumber());
                row.createCell(6).setCellValue(studentDto.getUserDto().getAddress());
                row.createCell(7).setCellValue(studentDto.getUserDto().is_active());
                row.createCell(8).setCellValue(studentDto.getStudentCode());
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
