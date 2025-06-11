package com.banking.backend.Banking_Backend.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_beneficiaries")
public class Beneficiary {
    @Id
    @Column(name = "beneficiary_id")
    @NotBlank(message = "Beneficiary ID is required")
    private Long Id;

    @Column(name = "beneficiary_name")
    @NotBlank(message = "Beneficiary name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 to 50 characters")
    private String beneficiaryName;

    @Column(name = "account_number")
    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "\\d{10,18}", message = "Account number must be between 10 to 18 digits")
    private String accountNumber;

    @Column(name = "bank_name")
    @NotBlank(message = "Bank name is required")
    private String bankName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
