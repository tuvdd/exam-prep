package com.project.exam_prep.entity;


import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Integer grade;


    @ManyToMany(mappedBy = "classes")
    private Set<Student> students;

    @ManyToMany
    @JoinTable(
            name = "ExaminationSetter_Class",
            joinColumns = @JoinColumn(name = "classId"),
            inverseJoinColumns = @JoinColumn(name = "examinationSetterId")
    )
    private Set<ExaminationSetter> examinationSetters;



    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<ExaminationSetter> getExaminationSetters() {
        return examinationSetters;
    }

    public void setExaminationSetters(Set<ExaminationSetter> examinationSetters) {
        this.examinationSetters = examinationSetters;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setCandidates(Set<Student> students) {
        this.students = students;
    }


}

