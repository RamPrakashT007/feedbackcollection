package com.feedback.feedbackCollection.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.feedback.feedbackCollection.data.entity.AdminData;
import com.feedback.feedbackCollection.data.entity.QuizSummaryEntity;
import com.feedback.feedbackCollection.data.entity.UserEntity;

import jakarta.transaction.Transactional;

@Repository
public interface AdminDataRepository extends JpaRepository<AdminData, Long> {
    @Transactional // Optional: Add this if you want the repository method to be transactional
    void deleteByUserId(Long userId);
    AdminData findByUserAndQuizSummary(UserEntity user, QuizSummaryEntity quizSummary);
}