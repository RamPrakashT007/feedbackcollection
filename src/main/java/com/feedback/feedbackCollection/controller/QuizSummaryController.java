package com.feedback.feedbackCollection.controller;

import com.feedback.feedbackCollection.service.QuizSummaryService;
import com.feedback.feedbackCollection.service.ResponseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://feedbackcollection-5wlz.onrender.com") // Replace with your frontend URL
@RestController
@RequestMapping("/api")
public class QuizSummaryController {

    @Autowired
    private QuizSummaryService quizSummaryService;

     @Autowired
    private  ResponseService responseService;
    
   
    @PostMapping("/save-quiz-summary")
public ResponseEntity<Map<String, Object>> saveQuizSummary(@RequestBody QuizSummaryRequest request) {
    Map<String, Object> response = new HashMap<>();
    try {
        System.out.println("Received quiz summary for userId: " + request.getUserId());
        
        // Save the quiz summary and get the quizSummaryId
        Long quizSummaryId = quizSummaryService.saveQuizSummary(
            request.getUserId(), 
            request.getTotalScore(), 
            request.getTotalTimeTaken()
        );

        // Add the quizSummaryId to the response
        response.put("quizSummaryId", quizSummaryId);
        response.put("message", "Quiz summary saved successfully!");
        
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        System.err.println("Error saving quiz summary: " + e.getMessage());
        response.put("error", "Failed to save quiz summary: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

    // Inner class to represent the request body
    public static class QuizSummaryRequest {
        private Long userId;
        private int totalTimeTaken;
        private int totalScore;

        // Getters and Setters
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
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
    }

     @GetMapping("/user-responses")
    public org.springframework.http.ResponseEntity<List<com.feedback.feedbackCollection.data.entity.ResponseEntity>> getUserResponses(@RequestParam Long userId) {
        List<com.feedback.feedbackCollection.data.entity.ResponseEntity> responses = responseService.getResponsesByUserId(userId);
        return ResponseEntity.ok(responses);
    }
}