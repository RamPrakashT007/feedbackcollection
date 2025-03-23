package com.feedback.feedbackCollection.service;


import com.feedback.feedbackCollection.data.entity.UserEntity;

public interface UserService {
    
    UserEntity saveUser(UserEntity userEntity);
    void updateCurrentQuestionIndex(Long userId, int currentQuestionIndex);
    UserEntity getUserById(Long userId);
}