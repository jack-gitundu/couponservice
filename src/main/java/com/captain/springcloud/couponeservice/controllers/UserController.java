package com.captain.springcloud.couponeservice.controllers;

import com.captain.springcloud.couponeservice.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        boolean loginResponse = securityService.login(email, password, request, response);
        if (loginResponse) {
            return "index";
        }
        return "login";
    }
}
