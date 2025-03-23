package com.feedback.feedbackCollection.service;

import com.feedback.feedbackCollection.model.AdminDataResponse;

import java.util.List;

public interface AdminService {
    void saveAdminData(Long userId, Long quizSummaryId);
    void deleteResponse(Long userId);
    List<AdminDataResponse> getAdminData();
    boolean existsByUserIdAndQuizSummaryId(Long userId, Long quizSummaryId);
}