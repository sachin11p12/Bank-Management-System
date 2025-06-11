package com.banking.backend.Banking_Backend.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String accountNumber;

        @PositiveOrZero(message = "Account balance must be zero or positive")
        private double accountBalance;

        @NotBlank(message = "Account type is required")
        @Pattern(regexp = "^(Savings|Current|Fixed|Recurring)$", message = "Account type must be Savings, Current, Fixed, or Recurring")
        private String accountType;

        @NotBlank(message = "Account holder name is required")
        @Size(min = 2, max = 100, message = "Account holder name must be between 2 and 100 characters")
        private String accountHolderName;

        @PositiveOrZero(message = "Interest rate must be zero or positive")
        private double interestRate;

        @Size(max = 500, message = "Status description must be less than 500 characters")
        private String accountStatusDescription;

        @NotNull(message = "Account open date is required")
        private LocalDate accountOpenDate;

        // Account close date is optional
        private LocalDate accountCloseDate;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        @NotNull(message = "User must be linked to the account")
        private User user;

}
