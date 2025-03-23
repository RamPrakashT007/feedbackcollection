package com.feedback.feedbackCollection.service.impl;

import com.feedback.feedbackCollection.data.entity.AdminData;
import com.feedback.feedbackCollection.data.entity.QuizSummaryEntity;
import com.feedback.feedbackCollection.data.entity.UserEntity;
import com.feedback.feedbackCollection.data.repository.AdminDataRepository;
import com.feedback.feedbackCollection.data.repository.QuizSummaryRepository;
import com.feedback.feedbackCollection.data.repository.UserRepository;
import com.feedback.feedbackCollection.model.AdminDataResponse;
import com.feedback.feedbackCollection.service.AdminService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceimpl implements AdminService {

    @Autowired
    private AdminDataRepository adminDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizSummaryRepository quizSummaryRepository;

    @Override
    @Transactional
    public void saveAdminData(Long userId, Long quizSummaryId) {
        try {
            // Fetch the user and quiz summary entities
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            QuizSummaryEntity quizSummary = quizSummaryRepository.findById(quizSummaryId)
                    .orElseThrow(() -> new RuntimeException("Quiz summary not found with ID: " + quizSummaryId));

            // Create and save the AdminData entity
            AdminData adminData = new AdminData();
            adminData.setUser(user);
            adminData.setQuizSummary(quizSummary);
            adminDataRepository.save(adminData);

            // Log the saved admin data
            System.out.println("Admin data saved: " + adminData);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error saving admin data: " + e.getMessage());
            throw e; // Re-throw the exception to propagate it to the controller
        }
    }

    @Override
    @Transactional // Add this annotation
    public void deleteResponse(Long userId) {
        adminDataRepository.deleteByUserId(userId);
    }

    @Override
public List<AdminDataResponse> getAdminData() {
    List<AdminDataResponse> adminDataResponses = new ArrayList<>();
    List<AdminData> adminDataList = adminDataRepository.findAll();

    for (AdminData adminData : adminDataList) {
        UserEntity user = adminData.getUser();
        QuizSummaryEntity quizSummary = adminData.getQuizSummary();

        AdminDataResponse response = new AdminDataResponse(
            user.getId(), // Use the ID from UserEntity
            user.getName(),
            user.getEmail(),
            quizSummary.getTotalScore(),
            quizSummary.getTotalTimeTaken()
        );
        adminDataResponses.add(response);
    }

    return adminDataResponses;
}
@Override
public boolean existsByUserIdAndQuizSummaryId(Long userId, Long quizSummaryId) {
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    QuizSummaryEntity quizSummary = quizSummaryRepository.findById(quizSummaryId)
        .orElseThrow(() -> new RuntimeException("Quiz summary not found with ID: " + quizSummaryId));

    // Check if the admin data exists
    return adminDataRepository.findByUserAndQuizSummary(user, quizSummary) != null;
}

}