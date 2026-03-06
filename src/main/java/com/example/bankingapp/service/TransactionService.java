package com.example.bankingapp.service;

import com.example.bankingapp.dto.TransactionDto;
import com.example.bankingapp.model.TransactionType;
import com.example.bankingapp.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    void saveTransaction(User user, BigDecimal amount, TransactionType type);
    List<TransactionDto> getTransactionsForAccount(String accountNumber, LocalDate startDate, LocalDate endDate);

}
