package com.feedback.feedbackCollection.service;

public interface QuizSummaryService {

    /**
     * Saves the quiz summary for a specific user.
     *
     * @param userId          The ID of the user who completed the quiz.
     * @param totalTimeTaken  The total time taken to complete the quiz (in seconds).
     * @param totalScore      The total score achieved by the user in the quiz.
     * @throws RuntimeException if the user is not found or an error occurs while saving the quiz summary.
     */
    Long saveQuizSummary(Long userId, int totalTimeTaken, int totalScore);
}