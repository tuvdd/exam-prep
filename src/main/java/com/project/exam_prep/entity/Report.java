package com.project.exam_prep.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reporter_name", nullable = false)
    private String reporterName;

    @Column(name = "reporter_code", nullable = false)
    private String reporterCode;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "is_processed", nullable = false)
    private Boolean isProcessed;


}
