package com.example.architectai.controller;

import com.example.architectai.dto.SignInDto;
import com.example.architectai.dto.SignUpDto;
import com.example.architectai.dto.UserTransactionRequestDto;
import com.example.architectai.dto.UserUsageRequestDto;
import com.example.architectai.entity.ApplicationUser;
import com.example.architectai.entity.UserTransaction;
import com.example.architectai.entity.UserUsage;
import com.example.architectai.service.AuthService;
import com.fasterxml.jackson.databind.JsonMappingException;
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

    @GetMapping("/get-user")
    public String home() {
        return "Hello World";
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

    @PostMapping("/users/{userId}/usages")
    public ResponseEntity<UserUsage> createUsages(@PathVariable(value = "userId") UUID userId,
                                                  @RequestBody UserUsageRequestDto userUsageRequestDto) throws JsonMappingException {
        return ResponseEntity.ok(authService.createUserUsage(userId, userUsageRequestDto));
    }

    @PutMapping("/users/{userId}/usages/{usagesId}")
    public ResponseEntity<UserUsage> updateUsages(@PathVariable(value = "userId") UUID userId,
                                                  @PathVariable(value = "usagesId") UUID usagesId,
                                                  @RequestBody UserUsageRequestDto userUsageRequestDto) throws JsonMappingException {
        return ResponseEntity.ok(authService.updateUserUsage(userId, usagesId, userUsageRequestDto));
    }

    @DeleteMapping("/users/{userId}/usages/{usagesId}")
    public ResponseEntity<Void> deleteUsages(@PathVariable(value = "userId") UUID userId,
                                             @PathVariable(value = "usagesId") UUID usagesId) {
        authService.deleteUserUsage(userId, usagesId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}/transaction")
    public ResponseEntity<List<UserTransaction>> getTransactions(@PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.ok(authService.getUserTransactions(userId));
    }

    @PostMapping("/users/{userId}/transaction")
    public ResponseEntity<UserTransaction> createTransaction(@PathVariable(value = "userId") UUID userId,
                                                             @RequestBody UserTransactionRequestDto userTransactionRequestDto) throws JsonMappingException {
        return ResponseEntity.ok(authService.createUserTransaction(userId, userTransactionRequestDto));
    }
    @PutMapping("/users/{userId}/transaction/{transactionId}")
    public ResponseEntity<UserTransaction> updateTransaction(@PathVariable(value = "userId") UUID userId,
                                                             @PathVariable(value = "transactionId") UUID transactionId,
                                                             @RequestBody UserTransactionRequestDto userTransactionRequestDto) throws JsonMappingException {
        return ResponseEntity.ok(authService.updateUserTransaction(userId, transactionId, userTransactionRequestDto));
    }

    @DeleteMapping("/users/{userId}/transaction/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable(value = "userId") UUID userId,
                                                  @PathVariable(value = "transactionId") UUID transactionId) {
        authService.deleteUserTransaction(userId, transactionId);
        return ResponseEntity.ok().build();
    }
}
