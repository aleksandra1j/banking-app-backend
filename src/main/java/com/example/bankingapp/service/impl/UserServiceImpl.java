package com.example.bankingapp.service.impl;

import com.example.bankingapp.dto.*;
import com.example.bankingapp.model.AccountStatus;
import com.example.bankingapp.model.Gender;
import com.example.bankingapp.model.TransactionType;
import com.example.bankingapp.model.User;
import com.example.bankingapp.repository.UserRepository;
import com.example.bankingapp.service.TransactionService;
import com.example.bankingapp.service.UserService;
import com.example.bankingapp.utils.AccountUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, TransactionService transactionService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BankResponse createAccount(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .build();
        }

        String accountNumber;
        do {
            accountNumber = AccountUtils.generateAccountNumber();
        } while (userRepository.existsByAccountNumber(accountNumber));

        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(Gender.valueOf(request.getGender().toString()))
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .accountNumber(accountNumber)
                .accountBalance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .build();

        userRepository.save(newUser);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(newUser.getFirstName() + " " + newUser.getLastName())
                        .accountNumber(newUser.getAccountNumber())
                        .accountBalance(newUser.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public AccountInfo getAccountInfo(String accountNumber) {
        User user = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE));

        return AccountInfo.builder()
                .accountName(user.getFirstName() + " " + user.getLastName())
                .accountNumber(user.getAccountNumber())
                .accountBalance(user.getAccountBalance())
                .build();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        User user = userRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE));

        user.setAccountBalance(
                user.getAccountBalance().add(request.getAmount())
        );

        userRepository.save(user);

        transactionService.saveTransaction(user, request.getAmount(), TransactionType.CREDIT);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .build();

    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        User user = userRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE));

        if (user.getAccountBalance().compareTo(request.getAmount()) < 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .build();
        }

        user.setAccountBalance(
                user.getAccountBalance().subtract(request.getAmount())
        );

        userRepository.save(user);
        transactionService.saveTransaction(user, request.getAmount(), TransactionType.DEBIT);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                .build();
    }

    @Override
    @Transactional
    public BankResponse transfer(TransferRequest request) {
        User sourceUser = userRepository.findByAccountNumber(request.getSourceAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException("Source account not found"));

        User destinationUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException("Destination account not found"));

        if (sourceUser.getAccountBalance().compareTo(request.getAmount()) < 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .build();
        }

        sourceUser.setAccountBalance(
                sourceUser.getAccountBalance().subtract(request.getAmount())
        );
        destinationUser.setAccountBalance(
                destinationUser.getAccountBalance().add(request.getAmount())
        );

        userRepository.save(sourceUser);
        userRepository.save(destinationUser);

        transactionService.saveTransaction(sourceUser, request.getAmount(), TransactionType.DEBIT);
        transactionService.saveTransaction(destinationUser, request.getAmount(), TransactionType.CREDIT);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .build();
    }
}
