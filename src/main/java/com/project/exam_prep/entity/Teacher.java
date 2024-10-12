package com.project.exam_prep.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private Integer id;
    @Column(name = "teacher_code", nullable =true, unique = true)
    private String teacherCode;

    private String department;
    private String position;
    private String expertiseArea;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "teacher")
    private List<QuestionSet> questionSets;

    @ManyToMany(mappedBy = "teachers")
    private Set<Class> classes;
}
