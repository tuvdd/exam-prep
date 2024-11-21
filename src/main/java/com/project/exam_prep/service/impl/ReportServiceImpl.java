package com.project.exam_prep.service.impl;

import com.project.exam_prep.dto.ReportDto;
import com.project.exam_prep.entity.Report;
import com.project.exam_prep.repo.ReportRepo;
import com.project.exam_prep.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepo reportRepo;

    @Override
    public List<ReportDto> getAllReport() {
        List<ReportDto> reportDtoList = new ArrayList<>();
        List<Report> reportList = reportRepo.findAll();
        for(Report report: reportList) {
            reportDtoList.add(new ReportDto(report));
        }

        return reportDtoList;
    }

    @Override
    public List<ReportDto> getReportByIsProcessed(boolean IsProcessed) {
        List<ReportDto> reportDtoList = new ArrayList<>();
        List<Report> reportList = reportRepo.getReportsByIsProcessed(IsProcessed);
        for(Report report: reportList) {
            reportDtoList.add(new ReportDto(report));
        }

        return reportDtoList;
    }

    @Override
    public boolean addReport(ReportDto reportDto) {
        reportRepo.save(ReportDto.convert(reportDto));
        return true;
    }

    @Override
    public ReportDto updateReport(ReportDto reportDto) {
        if(!reportRepo.existsById(reportDto.getId())) return null;
        reportRepo.save(ReportDto.convert(reportDto));
        return new ReportDto(reportRepo.findById(reportDto.getId()).get());
    }

    @Override
    public boolean deleteReport(int reportId) {
        if(!reportRepo.existsById(reportId)) return false;
        reportRepo.deleteById(reportId);
        return true;
    }
}
