package com.foodfactory.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String redirectToMenu() {
        return "redirect:/menu_interface"; // Redirects to /menu_interface
    }
}
