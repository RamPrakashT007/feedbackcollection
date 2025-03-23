package com.feedback.feedbackCollection.data.repository;

import com.feedback.feedbackCollection.data.entity.ResponseEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Long> {
    
    @Modifying // Indicates that this method modifies the database
    @Transactional // Ensures the method runs within a transaction
    @Query("DELETE FROM ResponseEntity r WHERE r.user.id = :userId AND r.questionId = :questionId")
    void deleteByIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") int questionId);

    @Query("SELECT r FROM ResponseEntity r WHERE r.user.id = :userId")
    List<ResponseEntity> findByUserId(@Param("userId") Long userId);
}
