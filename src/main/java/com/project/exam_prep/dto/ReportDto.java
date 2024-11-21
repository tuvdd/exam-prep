package com.project.exam_prep.dto;

import com.project.exam_prep.entity.Report;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportDto {
    private Integer id;
    private String reporterName;
    private String reporterCode;
    private String reason;
    private Boolean isProcessed;

    public ReportDto(Report report) {
        this.id = report.getId();
        this.reporterName = report.getReporterName();
        this.reporterCode = report.getReporterCode();
        this.reason = report.getReason();
        this.isProcessed = report.getIsProcessed();
    }

    public static Report convert(ReportDto reportDto) {
        return new Report(
                reportDto.getId(),
                reportDto.getReporterName(),
                reportDto.getReporterCode(),
                reportDto.getReason(),
                reportDto.getIsProcessed()
        );
    }
}
