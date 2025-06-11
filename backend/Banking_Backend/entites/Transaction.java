package com.banking.backend.Banking_Backend.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_transcation")
public class Transaction {
    @Id
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull(message = "Account reference is required")
    private Account account;

    @Column(name = "amount")
    @Positive(message = "Transaction amount must be greater than 0")
    private double amount;

    @Column(name = "description")
    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 100, message = "Description must be between 3 to 100 characters")
    private String description;

    @Column(name = "type")
    @NotBlank(message = "Transaction type is required")
    @Pattern(regexp = "DEPOSIT|WITHDRAWAL|TRANSFER", message = "Transaction type must be DEPOSIT, WITHDRAWAL, or TRANSFER")
    private String type;

    @Column(name = "balance")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private double balance;

    @Column(name = "timestamp")
    @NotBlank(message = "Timestamp is required")
    private String timestamp;


    private String status;

    private String createdAt;




}
