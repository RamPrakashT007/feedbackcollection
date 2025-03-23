package com.feedback.feedbackCollection.model;

public class ResponseRequest {
    private String userEmail;
    private int questionId;
    private String selectedOption;
    private String correctAnswer;
    private int timeTaken;
    
    public int getTimeTaken() {
        return timeTaken;
    }
    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public String getSelectedOption() {
        return selectedOption;
    }
    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // Getters and Setters
    
}