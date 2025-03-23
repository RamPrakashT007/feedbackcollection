package com.feedback.feedbackCollection.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feedback.feedbackCollection.data.entity.UserEntity;
import com.feedback.feedbackCollection.model.UpdateQuestionIndexRequest;
import com.feedback.feedbackCollection.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    private final UserService userService;
   
    public UserController(UserService userService) {
        this.userService = userService;
    }
   
    @PostMapping
    public ResponseEntity<Long> saveUser(@RequestBody UserEntity user) {
        System.out.println("🔹 Received User Data: " + user); // Debugging
        UserEntity savedUser = userService.saveUser(user);
        System.out.println("✅ Saved User: " + savedUser);
        return ResponseEntity.ok(savedUser.getId()); // Returns 200 OK with the saved user data
    }

    @PostMapping("/update-current-question-index")
    public ResponseEntity<Map<String, String>> updateCurrentQuestionIndex(
            @RequestBody UpdateQuestionIndexRequest request) {
        System.out.println("Updating current question index for user: " + request.getUserId());
        System.out.println("New index: " + request.getCurrentQuestionIndex());

        // Call the service method to update the current question index
        userService.updateCurrentQuestionIndex(request.getUserId(), request.getCurrentQuestionIndex());

        // Return a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Current question index updated successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long userId) {
        UserEntity user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
   

}
