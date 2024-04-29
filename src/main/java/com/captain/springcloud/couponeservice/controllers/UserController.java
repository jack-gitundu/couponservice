package com.captain.springcloud.couponeservice.controllers;

import com.captain.springcloud.couponeservice.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    public String login(@RequestParam String username, String password, HttpServletRequest request, HttpServletResponse response) {
        boolean loginResponse = securityService.login(username, password, request, response);
        if (loginResponse) {
            return "index";
        }
        return "login";
    }
}
