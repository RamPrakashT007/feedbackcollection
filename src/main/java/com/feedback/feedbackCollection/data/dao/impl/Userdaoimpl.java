package com.feedback.feedbackCollection.data.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.feedback.feedbackCollection.data.dao.Userdao;
import com.feedback.feedbackCollection.data.entity.UserEntity;
import com.feedback.feedbackCollection.data.repository.UserRepository;

@Repository
public class Userdaoimpl implements Userdao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity saveUser(UserEntity userEntity) {

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw e;
        }
       
        
        return userEntity;
       
    }
    
}
