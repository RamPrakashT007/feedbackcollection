package com.feedback.feedbackCollection.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_data")
public class AdminData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "quiz_summary_id", nullable = false)
    private QuizSummaryEntity quizSummary;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public QuizSummaryEntity getQuizSummary() {
        return quizSummary;
    }

    public void setQuizSummary(QuizSummaryEntity quizSummary) {
        this.quizSummary = quizSummary;
    }
}