package com.project.exam_prep.mapper;

import com.project.exam_prep.dto.ReportDto;
import com.project.exam_prep.entity.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public Report convertToEntity(ReportDto reportDto) {
        return new Report(
                reportDto.getId(),
                reportDto.getReporterName(),
                reportDto.getReporterCode(),
                reportDto.getReason(),
                reportDto.getIsProcessed()
        );
    }
}
