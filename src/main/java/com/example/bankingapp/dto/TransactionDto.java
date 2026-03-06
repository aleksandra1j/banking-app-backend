package com.example.bankingapp.dto;

import com.example.bankingapp.model.TransactionStatus;
import com.example.bankingapp.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private UUID transactionId;
    private TransactionType transactionType;

    private BigDecimal amount;
    private TransactionStatus status;
    private String reference;
    private String accountNumber;
    private LocalDateTime createdAt;
}
