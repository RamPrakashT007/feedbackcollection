package com.feedback.feedbackCollection.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_summary")
public class QuizSummaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_summary_id")
    private Long quizSummaryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "total_time_taken", nullable = false)
    private int totalTimeTaken;

    @Column(name = "total_score", nullable = false)
    private int totalScore;

    @Column(name = "timestamp", updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    // Getters and Setters
    public Long getQuizSummaryId() {
        return quizSummaryId;
    }

    public void setQuizSummaryId(Long quizSummaryId) {
        this.quizSummaryId = quizSummaryId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(int totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}