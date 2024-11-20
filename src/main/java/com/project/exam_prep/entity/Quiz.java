package com.project.exam_prep.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private Timestamp startTime;

    @Column(nullable = false)
    private Timestamp endTime;

    //public or private
    @Column(nullable = false)
    private String type;

    //test or practice
    @Column(nullable = false)
    private String mode;

    @ManyToMany(mappedBy = "quizzes")
    private Set<Student> students;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Result> results;

    @OneToOne
    @JoinColumn(name = "question_set_id")
    private QuestionSet questionSet;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}

