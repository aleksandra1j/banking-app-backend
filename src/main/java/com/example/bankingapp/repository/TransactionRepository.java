package com.example.bankingapp.repository;

import com.example.bankingapp.model.Transaction;
import com.example.bankingapp.model.TransactionStatus;
import com.example.bankingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByUserOrderByCreatedAtDesc(User user);
    Optional<Transaction> findByReference(String reference);


    @Query("SELECT t FROM Transaction t WHERE " +
            "t.user.accountNumber = :accountNumber AND t.createdAt BETWEEN :start AND :end")
    List<Transaction> findByUserAccountNumberBetweenDates(@Param("accountNumber") String accountNumber,
                                                          @Param("start") LocalDateTime start,
                                                          @Param("end") LocalDateTime end);
}
