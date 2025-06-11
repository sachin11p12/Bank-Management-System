package com.banking.backend.Banking_Backend.services;

import com.banking.backend.Banking_Backend.dto.TransactionDTO;
import com.banking.backend.Banking_Backend.request.TransactionRequest;

import java.util.List;

public interface TransactionService {

    TransactionDTO applyLoan(TransactionRequest request);
    List<TransactionDTO> getTranscationById(Long userId);
}
