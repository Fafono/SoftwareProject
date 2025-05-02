package com.foodfactory.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    @GetMapping({"/admin_login"})
    public String showLoginForm() {
        return "admin_login"; // Maps to admin_login.html
    }



}
