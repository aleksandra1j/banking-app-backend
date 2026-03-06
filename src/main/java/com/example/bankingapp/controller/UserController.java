package com.example.bankingapp.controller;

import com.example.bankingapp.dto.*;
import com.example.bankingapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Account Management APIs")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create New User Account", description = "Create a user and assign an account number")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping
    public BankResponse createAccount(@Valid @RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @Operation(summary = "Get Account Info", description = "Given an account number, get user's account info (name + balance)")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/info")
    public AccountInfo getAccountInfo(@RequestParam String accountNumber){
        return userService.getAccountInfo(accountNumber);
    }

    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
    }
}
