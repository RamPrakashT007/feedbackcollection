package com.feedback.feedbackCollection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/start-game")
    public String startGame() {
        return "user-details"; // ✅ Redirects to user-details.html
    }
    @GetMapping("/instructions")
    public String instructions() {
        return "instructions"; // ✅ Redirects to instructions.html
    }

    @GetMapping("/GamePage")
    public String GamePage() {
        return "user"; // ✅ Loads user.html from templates/
    }
    @GetMapping("/admin-login")
    public String adminLogin() {
        return "admin-login"; // ✅ Redirects to admin-login.html in the templates directory
    }
    @GetMapping("/admin")
    public String adminPanel() {
        return "admin"; // Redirects to admin.html
    }

    @GetMapping("/score")
    public String score() {
        return "score"; // Serve score.html from src/main/resources/templates
    }
}

