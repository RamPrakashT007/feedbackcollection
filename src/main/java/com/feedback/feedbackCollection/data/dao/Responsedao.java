package com.feedback.feedbackCollection.data.dao;

import java.util.List;
import java.util.Optional;

import com.feedback.feedbackCollection.data.entity.ResponseEntity;


public interface Responsedao {
    ResponseEntity saveResponse(ResponseEntity response);
    void clearResponse(Long userId, int questionId);
    List<ResponseEntity> findById(Long userId);
}