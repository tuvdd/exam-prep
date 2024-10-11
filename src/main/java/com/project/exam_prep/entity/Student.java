package com.project.exam_prep.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private int id;

    @Column(name = "student_code", nullable = true, unique = true)
    private String studentCode;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "students")
    private Set<Class> classes;

    @ManyToMany
    @JoinTable(
            name = "student_quiz",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private Set<Quiz> quizzes;

    @OneToMany(mappedBy = "student")
    private Set<Result> results;
}
