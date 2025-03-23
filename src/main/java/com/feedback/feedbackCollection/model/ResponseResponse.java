package com.feedback.feedbackCollection.model;

public class ResponseResponse {
    private Long id;
    private String userEmail;
    private int questionId;
    private String selectedOption;
    private String correctAnswer;
    private boolean isCorrect;
    private int timeTaken;
    public int getTimeTaken() {
        return timeTaken;
    }
    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public boolean isCorrect() {
        return isCorrect;
    }
    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    
}