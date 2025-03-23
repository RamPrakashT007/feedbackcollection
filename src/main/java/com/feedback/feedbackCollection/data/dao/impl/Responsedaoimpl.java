package com.feedback.feedbackCollection.data.dao.impl;

import com.feedback.feedbackCollection.data.dao.Responsedao;
import com.feedback.feedbackCollection.data.entity.ResponseEntity;
import com.feedback.feedbackCollection.data.repository.ResponseRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Responsedaoimpl implements Responsedao {

    @Autowired
    private ResponseRepository responseRepository;

    @Override
    public ResponseEntity saveResponse(ResponseEntity response) {
        return responseRepository.save(response);
    }

    @Override
    public void clearResponse(Long userId, int questionId) {
        responseRepository.deleteByIdAndQuestionId(userId, questionId);
    }

    @Override
    public List<ResponseEntity> findById(Long userId) {
        return responseRepository.findByUserId(userId);
    }
}