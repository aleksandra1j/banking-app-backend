package com.example.bankingapp.service.impl;

import com.example.bankingapp.dto.TransactionDto;
import com.example.bankingapp.model.Transaction;
import com.example.bankingapp.model.TransactionStatus;
import com.example.bankingapp.model.TransactionType;
import com.example.bankingapp.model.User;
import com.example.bankingapp.repository.TransactionRepository;
import com.example.bankingapp.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void saveTransaction(User user, BigDecimal amount, TransactionType type) {

        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setReference(generateReference());
        transaction.setDescription(type.name() + " transaction");

        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> getTransactionsForAccount(String accountNumber,
                                                          LocalDate  startDate,
                                                          LocalDate  endDate) {


        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<Transaction> transactions =
                transactionRepository.findByUserAccountNumberBetweenDates(
                        accountNumber,
                        startDateTime,
                        endDateTime
                );

        return transactions.stream().map(tx -> TransactionDto.builder()
                        .transactionId(tx.getTransactionId())
                        .transactionType(tx.getTransactionType())
                        .amount(tx.getAmount())
                        .status(tx.getStatus())
                        .reference(tx.getReference())
                        .accountNumber(tx.getUser().getAccountNumber())
                        .createdAt(tx.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private String generateReference() {
        return UUID.randomUUID().toString();
    }

}
