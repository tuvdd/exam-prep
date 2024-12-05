package com.project.exam_prep.controller;

import com.project.exam_prep.dto.ReportDto;
import com.project.exam_prep.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TAM THOI DE LAI CHUA XOA
@RestController
@CrossOrigin
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/save")
    public ResponseEntity<?> saveReport(@RequestBody ReportDto reportDto) {
        boolean check = reportService.addReport(reportDto);
        if(!check) return new ResponseEntity<>("Failed to save Report", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Save report successfully", HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getAllReport() {
        return ResponseEntity.ok(reportService.getAllReport());
    }

    @GetMapping("/{IsProccesed}")
    public ResponseEntity<?> getReportByIsProccesed(@PathVariable Boolean IsProccesed) {
        return ResponseEntity.ok(reportService.getReportByIsProcessed(IsProccesed));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateReport(@RequestBody ReportDto reportDto) {
        ReportDto result = reportService.updateReport(reportDto);
        if(result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{reportId}")
    public ResponseEntity<?> deleteReport(@PathVariable Integer reportId) {
        boolean check = reportService.deleteReport(reportId);
        if(!check) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok("Delete report successfully");
    }
}
