package com.feedback.feedbackCollection.model;

public class AdminDataResponse {
    private Long userId;
    private String name;
    private String email;
    private int totalScore;
    private int totalTimeTaken;

    // Constructor
    public AdminDataResponse(Long userId, String name, String email, int totalScore, int totalTimeTaken) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.totalScore = totalScore;
        this.totalTimeTaken = totalTimeTaken;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(int totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }
}