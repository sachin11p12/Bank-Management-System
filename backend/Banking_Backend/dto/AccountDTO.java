package com.banking.backend.Banking_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String id;
    private String accountNumber;
    private String accountType;
    private Double balance;
    private UserSummaryDTO user;  // 👈 changed from UserDTO to UserSummaryDTO
}
