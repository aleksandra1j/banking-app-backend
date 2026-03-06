package com.example.bankingapp.service;

import com.example.bankingapp.dto.*;

public interface UserService {

    BankResponse createAccount(UserRequest request);
    AccountInfo getAccountInfo(String accountNumber);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);

}
