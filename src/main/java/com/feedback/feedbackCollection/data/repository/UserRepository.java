package com.feedback.feedbackCollection.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.feedback.feedbackCollection.data.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Modifying
    @Query("UPDATE UserEntity u SET u.currentQuestionIndex = :currentQuestionIndex WHERE u.id = :userId")
    void updateCurrentQuestionIndex(@Param("userId") Long userId, @Param("currentQuestionIndex") int currentQuestionIndex);
    Optional<UserEntity> findByEmail(String email);
}
