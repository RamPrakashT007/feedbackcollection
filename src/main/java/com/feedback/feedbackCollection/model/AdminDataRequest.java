package com.feedback.feedbackCollection.model;

public class AdminDataRequest {
    private Long userId;
    private Long quizSummaryId;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuizSummaryId() {
        return quizSummaryId;
    }

    public void setQuizSummaryId(Long quizSummaryId) {
        this.quizSummaryId = quizSummaryId;
    }
}