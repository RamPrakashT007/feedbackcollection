package com.feedback.feedbackCollection.data.repository;

import com.feedback.feedbackCollection.data.entity.QuizSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuizSummaryRepository extends JpaRepository<QuizSummaryEntity, Long> {
    Optional<QuizSummaryEntity> findByQuizSummaryId(Long quizSummaryId);
}