package com.feedback.feedbackCollection.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback.feedbackCollection.data.dao.Userdao;
import com.feedback.feedbackCollection.data.entity.UserEntity;
import com.feedback.feedbackCollection.data.repository.UserRepository;
import com.feedback.feedbackCollection.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    public Userdao userdao;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity saveUser(UserEntity userEntity) {

        return userdao.saveUser(userEntity);

    }

    @Override
    @Transactional
    public void updateCurrentQuestionIndex(Long userId, int currentQuestionIndex) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.setCurrentQuestionIndex(currentQuestionIndex);
        userRepository.save(user);
    }
    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}
