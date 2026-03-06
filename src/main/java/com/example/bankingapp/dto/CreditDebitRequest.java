package com.example.bankingapp.dto;

import com.example.bankingapp.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequest {
    //private TransactionType transactionType;
    @NotBlank
    private String accountNumber;

    @NotNull
    @Positive
    private BigDecimal amount;
}
