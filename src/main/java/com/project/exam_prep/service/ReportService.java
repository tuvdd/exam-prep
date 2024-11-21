package com.project.exam_prep.service;

import com.project.exam_prep.dto.ReportDto;

import java.util.List;

public interface ReportService {
    List<ReportDto> getAllReport();
    List<ReportDto> getReportByIsProcessed(boolean IsProcessed);
    boolean addReport(ReportDto reportDto);
    ReportDto updateReport(ReportDto reportDto);
    boolean deleteReport(int reportId);

}
