package com.feedback.feedbackCollection.service.impl;

import com.feedback.feedbackCollection.data.dao.Responsedao;
import com.feedback.feedbackCollection.data.entity.ResponseEntity;
import com.feedback.feedbackCollection.data.entity.UserEntity;
import com.feedback.feedbackCollection.data.repository.UserRepository;
import com.feedback.feedbackCollection.service.ResponseService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponseServiceimpl implements ResponseService {

    @Autowired
    private Responsedao responseDao;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository to fetch UserEntity

    @Override
    public ResponseEntity saveResponse(Long userId, int questionId, String selectedOption, String correctAnswer, int timeTaken, int isCorrect) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    
        ResponseEntity response = new ResponseEntity();
        response.setUser(user);
        response.setQuestionId(questionId);
        response.setSelectedOption(selectedOption);
        response.setCorrectAnswer(correctAnswer);
        response.setTimeTaken(timeTaken);
    
        // Use the isCorrect parameter directly
        response.setCorrect(isCorrect == 1);
    
        return responseDao.saveResponse(response);
    }

@Override
@Transactional // Add this annotation
public void clearResponse(Long userId, int questionId) {
    responseDao.clearResponse(userId, questionId);
}

@Override
public List<ResponseEntity> getResponsesByUserId(Long userId) {
    return responseDao.findById(userId);
}
}