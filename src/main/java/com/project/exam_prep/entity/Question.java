package com.project.exam_prep.entity;


import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String questionText;
    private String questionType;

    @ManyToMany(mappedBy = "questions")
    private Set<QuestionSet> questionSets;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionImage> questionImages;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<QuestionImage> getQuestionImages() {
        return questionImages;
    }

    public void setQuestionImages(List<QuestionImage> questionImages) {
        this.questionImages = questionImages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Set<QuestionSet> getQuestionSets() {
        return questionSets;
    }

    public void setQuestionSets(Set<QuestionSet> questionSets) {
        this.questionSets = questionSets;
    }
}

