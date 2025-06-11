package com.banking.backend.Banking_Backend.services.impl;

import com.banking.backend.Banking_Backend.dto.TransactionDTO;
import com.banking.backend.Banking_Backend.entites.Account;
import com.banking.backend.Banking_Backend.entites.Transaction;
import com.banking.backend.Banking_Backend.repository.AccountRepository;
import com.banking.backend.Banking_Backend.repository.TransactionRepository;
import com.banking.backend.Banking_Backend.request.TransactionRequest;
import com.banking.backend.Banking_Backend.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService {


        private final TransactionRepository transactionRepository;
        private final AccountRepository accountRepository;

        public TransactionDTO transfer(TransactionRequest request) {
            Account from = accountRepository.findById(request.getFromAccountId())
                    .orElseThrow(() -> new RuntimeException("Sender account not found"));
            Account to = accountRepository.findById(request.getToAccountId())
                    .orElseThrow(() -> new RuntimeException("Receiver account not found"));

            if (from.getAccountBalance() < request.getAmount()) {
                throw new RuntimeException("Insufficient balance");
            }

            from.setAccountBalance(from.getAccountBalance() - request.getAmount());
            to.setAccountBalance(to.getAccountBalance() + request.getAmount());

            accountRepository.save(from);
            accountRepository.save(to);

            Transaction txn = new Transaction();
            txn.setType("TRANSFER");
            txn.setAmount(request.getAmount());
            txn.setStatus("SUCCESS");
            txn.setCreatedAt(LocalDateTime.now().toString());

            transactionRepository.save(txn);

            return toDTO(txn);
        }

        private TransactionDTO toDTO(Transaction txn) {
            TransactionDTO dto = new TransactionDTO();
            dto.setId(txn.getId());
            dto.setType(txn.getType());
            dto.setAmount(txn.getAmount());
            dto.setStatus(txn.getStatus());
            dto.setCreatedAt(txn.getCreatedAt());
            return dto;
        }

        @Override
        public TransactionDTO applyLoan(TransactionRequest request) {
            return null;
        }

        @Override
        public List<TransactionDTO> getTranscationById(Long userId) {
            return transactionRepository.findByAccountId(userId)
                    .stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

