package com.banking.backend.Banking_Backend.services.impl;

import com.banking.backend.Banking_Backend.dto.AccountDTO;
import com.banking.backend.Banking_Backend.dto.UserDTO;
import com.banking.backend.Banking_Backend.dto.UserSummaryDTO;
import com.banking.backend.Banking_Backend.entites.Account;
import com.banking.backend.Banking_Backend.entites.User;
import com.banking.backend.Banking_Backend.repository.AccountRepository;
import com.banking.backend.Banking_Backend.repository.UserRepository;
import com.banking.backend.Banking_Backend.request.AccountRequest;

import com.banking.backend.Banking_Backend.services.AccountServices;
import com.banking.backend.Banking_Backend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class AccountServiceImpl implements AccountServices {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public ApiResponse <AccountDTO> createAccount(Long userId, AccountRequest request) {
        boolean alreadyExists = accountRepository.findByUser_UserId(userId).stream()
                .anyMatch(acc -> acc.getAccountType().equalsIgnoreCase(request.getAccountType()));

        if (alreadyExists) {
            return new ApiResponse<>(
                    false,
                    "You already have a " + request.getAccountType() + " account. Try another type like Fixed or Recurring.",
                    null
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Account account = new Account();
        account.setAccountNumber (generateUniqueAccountId());
        account.setAccountType(request.getAccountType());
        account.setAccountBalance(request.getInitialDeposit());
        account.setAccountHolderName(user.getName());
        account.setAccountOpenDate(java.time.LocalDate.now());
        account.setInterestRate(3.5);
        account.setAccountStatusDescription("Active");
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        AccountDTO accountDTO = new AccountDTO(
                savedAccount.getId().toString(),         // id (String)
                savedAccount.getAccountNumber(),         // accountNumber
                savedAccount.getAccountType(),            // accountType
                savedAccount.getAccountBalance(),         // balance
                new UserSummaryDTO (
                        user.getUserId(),
                        user.getName(),
                        user.getEmail()
                )
        );

        return new ApiResponse<>(
                true,
                "Account created successfully.",
                accountDTO
        );
    }


    @Override
    public Optional<Account> getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<AccountDTO> getAccountsByUserId(Long userId) {
        List<Account> accounts = accountRepository.findByUser_UserId(userId);

        return accounts.stream().map(account ->
                new AccountDTO(
                        account.getId().toString(),
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getAccountBalance(),
                        new UserSummaryDTO(
                                account.getUser ().getUserId(),
                                account.getUser ().getName(),
                                account.getUser ().getEmail()
                        )
                )
        ).collect(Collectors.toList());
    }

    // Helper method to convert Account entity to AccountDTO with full user details
    public AccountDTO convertToAccountDTO(Account account) {
        User user = account.getUser();

        UserSummaryDTO userSummaryDTO = new UserSummaryDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail()
        );

        return new AccountDTO(
                account.getId().toString(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getAccountBalance(),
                userSummaryDTO
        );
    }


    private String generateUniqueAccountId() {
        Optional < Account > lastAccountOpt = accountRepository.findTopByOrderByAccountNumberDesc ( );
        String lastAccountNumber = lastAccountOpt.map ( Account::getAccountNumber ).orElse ( "540000000" );
        long lastSerial = Long.parseLong ( lastAccountNumber.substring ( 2 ) );
        long newSerial = lastSerial + 1;
        return "54" + String.format ( "%07d" , newSerial );
    }

    @Override
    public ApiResponse<AccountDTO> deposit(String accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with accountNumber: " + accountNumber));

        account.setAccountBalance(account.getAccountBalance() + amount);
        Account updatedAccount = accountRepository.save(account);

        return new ApiResponse<>(
                true,
                "Deposit successful",
                convertToAccountDTO(updatedAccount)
        );
    }

    @Override
    public ApiResponse<AccountDTO> withdraw(String accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with accountNumber: " + accountNumber));

        if (account.getAccountBalance() < amount) {
            return new ApiResponse<>(false, "Insufficient funds", null);
        }

        account.setAccountBalance(account.getAccountBalance() - amount);
        Account updatedAccount = accountRepository.save(account);

        return new ApiResponse<>(
                true,
                "Withdrawal successful",
                convertToAccountDTO(updatedAccount)
        );
    }

    @Override
    public ApiResponse<AccountDTO> updateAccount(String accountNumber, AccountRequest request) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with accountNumber: " + accountNumber));

        // Update fields based on AccountRequest, for example:
        if (request.getAccountType() != null && !request.getAccountType().isEmpty()) {
            account.setAccountType(request.getAccountType());
        }
        // You can add more fields if your AccountRequest has any other updateable fields.

        Account updatedAccount = accountRepository.save(account);

        return new ApiResponse<>(
                true,
                "Account updated successfully",
                convertToAccountDTO(updatedAccount)
        );
    }

    @Override
    public ApiResponse<?> deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with accountNumber: " + accountNumber));

        accountRepository.delete(account);

        return new ApiResponse<>(
                true,
                "Account deleted successfully",
                null
        );
    }


}
