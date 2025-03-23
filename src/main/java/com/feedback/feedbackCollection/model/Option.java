package com.feedback.feedbackCollection.model;

public class Option {
    private String label;
    private String image;

    public Option(String label, String image) {
        this.label = label;
        this.image = image;
    }

    // ✅ Getters
    public String getLabel() { return label; }
    public String getImage() { return image; }
}
