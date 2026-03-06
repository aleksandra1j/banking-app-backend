package com.example.bankingapp.repository;

import com.example.bankingapp.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    Optional<User> findByAccountNumber(String accountNumber);
    Optional<User> findByEmail(String email);
}
