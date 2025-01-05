package com.project.exam_prep.controller;

import com.project.exam_prep.dto.StatisticsDto;
import com.project.exam_prep.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/api/statistics")
    public StatisticsDto getStatistics() {
        return statisticsService.getStatistics();
    }
}

