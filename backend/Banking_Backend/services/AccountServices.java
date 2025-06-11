package com.banking.backend.Banking_Backend.services;
import com.banking.backend.Banking_Backend.dto.AccountDTO;
import com.banking.backend.Banking_Backend.entites.Account;
import com.banking.backend.Banking_Backend.request.AccountRequest;
import com.banking.backend.Banking_Backend.utils.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface AccountServices {

        ApiResponse <AccountDTO> createAccount(Long userId, AccountRequest request);

        Optional<Account> getAccount(String accountNumber);
        List<AccountDTO> getAccountsByUserId(Long userId);

        public ApiResponse<?> deleteAccount(String accountNumber);
        public ApiResponse<AccountDTO> updateAccount(String accountNumber, AccountRequest request);
        public ApiResponse<AccountDTO> withdraw(String accountNumber, Double amount);
        public ApiResponse<AccountDTO> deposit(String accountNumber, Double amount);
        AccountDTO convertToAccountDTO (Account account);
}
