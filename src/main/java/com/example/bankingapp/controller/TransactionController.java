package com.example.bankingapp.controller;

import com.example.bankingapp.dto.AccountInfo;
import com.example.bankingapp.dto.BankResponse;
import com.example.bankingapp.dto.TransactionDto;
import com.example.bankingapp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/statement")
    public BankResponse getBankStatement(@RequestParam String accountNumber,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         LocalDate startDate,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                             LocalDate endDate) {

        List<TransactionDto> transactions = transactionService.getTransactionsForAccount(
                accountNumber, startDate, endDate
        );

        return BankResponse.builder()
                .responseCode("009")
                .responseMessage("Bank statement fetched successfully")
                .accountInfo(AccountInfo.builder()
                        .accountNumber(accountNumber)
                        .accountName("N/A")
                        .build())
                .transactions(transactions)
                .build();
    }
}
