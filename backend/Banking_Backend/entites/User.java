package com.banking.backend.Banking_Backend.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @Past(message = "Date of birth must be in the past")
    @Column(name = "dob")
    private LocalDate dob;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    private boolean isActive = true;
    private boolean isLocked = false;
    private boolean isEnabled = true;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Loan> loans;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Cards> cardsList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Beneficiary> beneficiariesList;

    // ----- Explicit Getters -----

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Role getRole() {
        return role;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<Cards> getCardsList() {
        return cardsList;
    }

    public List<Beneficiary> getBeneficiariesList() {
        return beneficiariesList;
    }

    // ----- UserDetails Interface Overrides -----

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Return authorities if needed
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Update logic if using expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Update logic if credentials expire
    }

    public void setName(@NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name) {
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email) {
    }

    public void setPassword(String encode) {
    }

    public void setPhoneNumber(@NotBlank(message = "Phone is required") String phone) {
    }

    public void setRole(Role role) {
    }

    public void setAddress(@NotBlank(message = "Address is required") String address) {
    }

    public void setDob(LocalDate dob) {
    }
}