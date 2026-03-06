package com.example.bankingapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    @Schema(description = "Full name of the account holder")
    private String accountName;

    @Schema(description = "Unique account number")
    private String accountNumber;

    @Schema(description = "Current account balance")
    private BigDecimal accountBalance;

}
