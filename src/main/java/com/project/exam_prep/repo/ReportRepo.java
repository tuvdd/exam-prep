package com.project.exam_prep.repo;

import com.project.exam_prep.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report, Integer> {

    List<Report> getReportsByIsProcessed(boolean isProcessed);
}
