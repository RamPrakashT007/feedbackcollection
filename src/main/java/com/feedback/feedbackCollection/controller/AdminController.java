package com.feedback.feedbackCollection.controller;

import com.feedback.feedbackCollection.model.AdminDataRequest;
import com.feedback.feedbackCollection.model.AdminDataResponse;
import com.feedback.feedbackCollection.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin-data")
    public ResponseEntity<List<AdminDataResponse>> getAdminData() {
        List<AdminDataResponse> adminData = adminService.getAdminData();
        return ResponseEntity.ok(adminData);
    }

    
    @DeleteMapping("/delete-response")
    public ResponseEntity<Map<String, String>> deleteResponse(@RequestBody AdminDataRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println("Received DELETE request for userId: " + request.getUserId());
            adminService.deleteResponse(request.getUserId());
            response.put("message", "Response deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error deleting response: " + e.getMessage());
            response.put("error", "Failed to delete response: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/save-admin-data")
    public ResponseEntity<Map<String, String>> saveAdminData(@RequestBody AdminDataRequest request) {
        try {
            adminService.saveAdminData(request.getUserId(), request.getQuizSummaryId());
    
            Map<String, String> response = new HashMap<>();
            response.put("message", "Admin data saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to save admin data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @GetMapping("/check-admin-data")
    public ResponseEntity<Map<String, Boolean>> checkAdminData(
            @RequestParam Long userId,
            @RequestParam Long quizSummaryId) {
        boolean exists = adminService.existsByUserIdAndQuizSummaryId(userId, quizSummaryId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
    
}