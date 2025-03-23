package com.feedback.feedbackCollection.service.impl;

import com.feedback.feedbackCollection.data.entity.QuizSummaryEntity;
import com.feedback.feedbackCollection.data.entity.UserEntity;
import com.feedback.feedbackCollection.data.repository.QuizSummaryRepository;
import com.feedback.feedbackCollection.data.repository.UserRepository;
import com.feedback.feedbackCollection.service.QuizSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizSummaryServiceimpl implements QuizSummaryService {

    @Autowired
    private QuizSummaryRepository quizSummaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Long saveQuizSummary(Long userId, int totalScore, int totalTimeTaken) {
        System.out.println("Saving quiz summary for userId: " + userId);
        System.out.println("Total Score: " + totalScore);
        System.out.println("Total Time Taken: " + totalTimeTaken);
    
        QuizSummaryEntity quizSummary = new QuizSummaryEntity();
        try {
            // Fetch the UserEntity using userId
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    
            quizSummary.setUser(user); // Set the UserEntity object
            quizSummary.setTotalScore(totalScore);
            quizSummary.setTotalTimeTaken(totalTimeTaken);
    
            // Save the quiz summary
            quizSummaryRepository.save(quizSummary);
    
            System.out.println("Quiz summary saved successfully for user: " + userId);
        } catch (Exception e) {
            System.err.println("Error saving quiz summary: " + e.getMessage());
            throw new RuntimeException("Failed to save quiz summary", e);
        }
        return quizSummary.getQuizSummaryId();
    }
}