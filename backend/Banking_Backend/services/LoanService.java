package com.banking.backend.Banking_Backend.services;

import com.banking.backend.Banking_Backend.dto.LoanDTO;
import com.banking.backend.Banking_Backend.request.LoanRequest;

import java.util.List;

public interface LoanService {
    LoanDTO applyLoan(LoanRequest request);
    List<LoanDTO> getLoansByUserId(Long userId);
}
