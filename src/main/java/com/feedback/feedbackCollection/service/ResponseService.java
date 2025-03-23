package com.feedback.feedbackCollection.service;


import java.util.List;
import java.util.Optional;

import com.feedback.feedbackCollection.data.entity.ResponseEntity;

public interface ResponseService {
    ResponseEntity saveResponse(Long userId, int questionId, String selectedOption, String correctAnswer,int isCorrect,int timeTaken);
    void clearResponse(Long userId, int questionId);
   List<ResponseEntity> getResponsesByUserId(Long userId);
}