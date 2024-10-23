package com.example.architectai.controller;

import com.example.architectai.dto.SignInDto;
import com.example.architectai.dto.SignUpDto;
import com.example.architectai.entity.ApplicationUser;
import com.example.architectai.entity.UserTransaction;
import com.example.architectai.entity.UserUsage;
import com.example.architectai.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApplicationUser> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApplicationUser> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(authService.signIn(signInDto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<ApplicationUser>> getUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/users/{userId}/usages")
    public ResponseEntity<UserUsage> getUsages(@PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.ok(authService.getUserUsage(userId));
    }

    @GetMapping("/users/{userId}/transaction")
    public ResponseEntity<List<UserTransaction>> getTransactions(@PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.ok(authService.getUserTransactions(userId));
    }
}
