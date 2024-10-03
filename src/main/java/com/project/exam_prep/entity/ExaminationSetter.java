package com.project.exam_prep.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ExaminationSetter")
public class ExaminationSetter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private int id;
    private String department;
    private String position;
    private String expertiseArea;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "examinationSetter")
    private List<QuestionSet> questionSets;

    @ManyToMany(mappedBy = "examinationSetters")
    private Set<Class> classes;

    public Set<Class> getClasses() {
        return classes;
    }

    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getExpertiseArea() {
        return expertiseArea;
    }

    public void setExpertiseArea(String expertiseArea) {
        this.expertiseArea = expertiseArea;
    }

    public List<QuestionSet> getQuestionSets() {
        return questionSets;
    }

    public void setQuestionSets(List<QuestionSet> questionSets) {
        this.questionSets = questionSets;
    }
}
