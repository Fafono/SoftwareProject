package com.foodfactory.food.controller;

import com.foodfactory.food.model.AuthenticationResponse;
import com.foodfactory.food.model.RegisterDTO;
import com.foodfactory.food.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register_form")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterDTO request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }


}
