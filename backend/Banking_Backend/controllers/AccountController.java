package com.banking.backend.Banking_Backend.controllers;

import com.banking.backend.Banking_Backend.dto.AccountDTO;
import com.banking.backend.Banking_Backend.entites.Account;
import com.banking.backend.Banking_Backend.request.AccountRequest;
import com.banking.backend.Banking_Backend.request.TransactionRequest;
import com.banking.backend.Banking_Backend.services.AccountServices;
import com.banking.backend.Banking_Backend.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Account Controller", description = "Endpoints for account management")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    @Operation(summary = "Open a new account",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Account created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/openAccount")
    public ResponseEntity<ApiResponse<AccountDTO>> createAccount(
            @RequestBody @Valid AccountRequest accountRequest) {
        ApiResponse<AccountDTO> response = accountServices.createAccount(accountRequest.getUserId(), accountRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get account by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Account fetched successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountDTO>> getAccount(@PathVariable String accountId) {
        Optional<Account> optionalAccount = accountServices.getAccount(accountId);

        if (optionalAccount.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>(false, "Account not found", null));
        }

        Account account = optionalAccount.get();
        AccountDTO accountDTO = accountServices.convertToAccountDTO(account);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Account fetched successfully", accountDTO)
        );
    }

    @Operation(summary = "Deposit funds into an account",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Deposit successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<ApiResponse<AccountDTO>> depositFunds(
            @PathVariable String accountId,
            @RequestBody @Valid TransactionRequest transactionRequest) {
        ApiResponse<AccountDTO> response = accountServices.deposit(accountId, transactionRequest.getAmount());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Withdraw funds from an account",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Withdrawal successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Insufficient funds",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<ApiResponse<AccountDTO>> withdrawFunds(
            @PathVariable String accountId,
            @RequestBody @Valid TransactionRequest transactionRequest) {
        ApiResponse<AccountDTO> response = accountServices.withdraw(accountId, transactionRequest.getAmount());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update account details",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Account updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PutMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountDTO>> updateAccount(
            @PathVariable String accountId,
            @RequestBody @Valid AccountRequest accountRequest) {
        ApiResponse<AccountDTO> response = accountServices.updateAccount(accountId, accountRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an account",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Account deleted successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponse<?>> deleteAccount(@PathVariable String accountId) {
        accountServices.deleteAccount(accountId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account deleted successfully", null));
    }
}
