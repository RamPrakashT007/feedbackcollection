package com.feedback.feedbackCollection.model;

import java.util.List;

public class Question {
    private int id;
    private String image1;
    private String image2;
    private String image3;
    private String correctAnswer;
    private List<Option> options;

    public Question(int id, String image1, String image2, String image3, String correctAnswer, List<Option> options) {
        this.id = id;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    // ✅ Getters
    public int getId() { return id; }
    public String getImage1() { return image1; }
    public String getImage2() { return image2; }
    public String getImage3() { return image3; }
    public String getCorrectAnswer() { return correctAnswer; }
    public List<Option> getOptions() { return options; }
}
