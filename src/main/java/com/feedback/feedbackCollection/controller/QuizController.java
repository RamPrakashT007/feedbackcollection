package com.feedback.feedbackCollection.controller;

import com.feedback.feedbackCollection.model.Question;
import com.feedback.feedbackCollection.model.Option;
import com.feedback.feedbackCollection.service.ResponseService;
import com.feedback.feedbackCollection.data.entity.ResponseDto;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // ✅ Allows requests from any frontend
public class QuizController {

    @Autowired
    private ResponseService responseService;

    // ✅ Question-related endpoints
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
    /** Returns a list of questions with image paths */
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getQuestions() {
        List<Question> questions = Arrays.asList(
            new Question(1, "/images/image1.1.png", "/images/image1.2.png", "/images/image1.3.png", "Option C",
                Arrays.asList(
                    new Option("Option A", "/images/image 1.4.png"),
                    new Option("Option B", "/images/image1.5.png"),
                    new Option("Option C", "/images/image1.6.png"),
                    new Option("Option D", "/images/image1.7.png"),
                    new Option("Option E", "/images/image1.8.png")
                )
            ),
            new Question(2, "/images/image2.1.png", "/images/image2.2.png", "/images/image2.3.png", "Option E",
                Arrays.asList(
                    new Option("Option A", "/images/image2.4.png"),
                    new Option("Option B", "/images/image2.5.png"),
                    new Option("Option C", "/images/image2.6.png"),
                    new Option("Option D", "/images/image2.7.png"),
                    new Option("Option E", "/images/image2.8.png")
                )
            )
            ,
            new Question(3, "/images/image3.1.png", "/images/image3.2.png", "/images/image3.3.png", "Option E",
                Arrays.asList(
                    new Option("Option A", "/images/image3.4.png"),
                    new Option("Option B", "/images/image3.5.png"),
                    new Option("Option C", "/images/image3.6.png"),
                    new Option("Option D", "/images/image3.7.png"),
                    new Option("Option E", "/images/image3.8.png")
                )
            )
            ,
            new Question(4, "/images/image4.1.png", "/images/image4.2.png", "/images/image4.3.png", "Option E",
                Arrays.asList(
                    new Option("Option A", "/images/image4.4.png"),
                    new Option("Option B", "/images/image4.5.png"),
                    new Option("Option C", "/images/image4.6.png"),
                    new Option("Option D", "/images/image4.7.png"),
                    new Option("Option E", "/images/image4.8.png")
                )
            )
            ,
            new Question(5, "/images/image5.1.png", "/images/image5.2.png", "/images/image5.3.png", "Option D",
                Arrays.asList(
                    new Option("Option A", "/images/image5.4.png"),
                    new Option("Option B", "/images/image5.5.png"),
                    new Option("Option C", "/images/image5.6.png"),
                    new Option("Option D", "/images/image5.7.png"),
                    new Option("Option E", "/images/image5.8.png")
                )
            )
            ,
            new Question(6, "/images/image6.1.png", "/images/image6.2.png", "/images/image6.3.png", "Option E",
                Arrays.asList(
                    new Option("Option A", "/images/image6.4.png"),
                    new Option("Option B", "/images/image6.5.png"),
                    new Option("Option C", "/images/image6.6.png"),
                    new Option("Option D", "/images/image6.7.png"),
                    new Option("Option E", "/images/image6.8.png")
                )
            )
            ,
            new Question(7, "/images/image7.1.png", "/images/image7.2.png", "/images/image7.3.png", "Option E",
                Arrays.asList(
                    new Option("Option A", "/images/image7.4.png"),
                    new Option("Option B", "/images/image7.5.png"),
                    new Option("Option C", "/images/image7.6.png"),
                    new Option("Option D", "/images/image7.7.png"),
                    new Option("Option E", "/images/image7.8.png")
                )
            )
            ,
            new Question(8, "/images/image8.1.png", "/images/image8.2.png", "/images/image8.3.png", "Option B",
                Arrays.asList(
                    new Option("Option A", "/images/image8.4.png"),
                    new Option("Option B", "/images/image8.5.png"),
                    new Option("Option C", "/images/image8.6.png"),
                    new Option("Option D", "/images/image8.7.png"),
                    new Option("Option E", "/images/image8.8.png")
                )
            )
            ,
            new Question(9, "/images/image9.1.png", "/images/image9.2.png", "/images/image9.3.png", "Option D",
                Arrays.asList(
                    new Option("Option A", "/images/image9.4.png"),
                    new Option("Option B", "/images/image9.5.png"),
                    new Option("Option C", "/images/image9.6.png"),
                    new Option("Option D", "/images/image9.7.png"),
                    new Option("Option E", "/images/image9.8.png")
                )
            )
            ,
            new Question(10, "/images/image10.1.png", "/images/image10.2.png", "/images/image10.3.png", "Option C",
                Arrays.asList(
                    new Option("Option A", "/images/image10.4.png"),
                    new Option("Option B", "/images/image10.5.png"),
                    new Option("Option C", "/images/image10.6.png"),
                    new Option("Option D", "/images/image10.7.png"),
                    new Option("Option E", "/images/image10.8.png")
                )
            )
        );
        return ResponseEntity.ok(questions);
    }

    // ✅ Response-related endpoints

    @PostMapping("/save-response")
    public ResponseEntity<String> saveResponse(
            @RequestParam Long userId,
            @RequestBody ResponseDto responseDto) {
        logger.info("Received request to save response for userId: " + userId);
        logger.info("Response DTO: " + responseDto.toString()); 
        if (userId == null) {
            logger.error("User ID is null in the request payload");
            throw new IllegalArgumentException("User ID must not be null");
        }
        logger.info("Saving response for userId: " + userId);
        boolean isCorrect = responseDto.getSelectedOption().trim().equalsIgnoreCase(responseDto.getCorrectAnswer().trim());
        responseService.saveResponse(
                userId,
                responseDto.getQuestionId(),
                responseDto.getSelectedOption(),
                responseDto.getCorrectAnswer(),
                responseDto.getTimeTaken(),
                isCorrect ? 1 : 0);
        return ResponseEntity.ok("Response saved successfully");
    }

    /** Clears a user's response for a specific question */
    @DeleteMapping("/clear-response")
    public ResponseEntity<String> clearResponse(@RequestParam Long userId, @RequestParam int questionId) {
        responseService.clearResponse(userId, questionId);
        return ResponseEntity.ok("Response cleared successfully");
    }
}