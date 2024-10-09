package com.project.exam_prep.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questionSet")
public class QuestionSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(nullable = false)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "examinationSetterId", referencedColumnName = "id")
    private ExaminationSetter examinationSetter;

    @ManyToMany
    @JoinTable(
            name = "QuestionSetQuestion",
            joinColumns = @JoinColumn(name = "questionSetId"),
            inverseJoinColumns = @JoinColumn(name = "questionId")
    )
    private Set<Question> questions;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ExaminationSetter getExaminationSetter() {
        return examinationSetter;
    }

    public void setExaminationSetter(ExaminationSetter examinationSetter) {
        this.examinationSetter = examinationSetter;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
