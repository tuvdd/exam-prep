package com.project.exam_prep.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String answerText;
    @Column(nullable = false)
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
}

