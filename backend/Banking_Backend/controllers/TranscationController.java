package com.banking.backend.Banking_Backend.controllers;

import com.banking.backend.Banking_Backend.entites.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user/transcation")
public class TranscationController {

//=============================================================================================

    //1.getAllTranscation() method to get all transactions of a user from the database.
    //2. deposite() method to create a new deposit transaction.
    //3. withdraw() method to create a new withdraw transaction.
    //4. transfer() method to create a new transfer transaction.
    //5. getDeposit() method to get all deposit transactions of a user from the database.
    //6. getWithdraw() method to get all withdraw transactions of a user from the database.
    //7. getTransfer() method to get all transfer transactions of a user from the database.
    //8. getTransactionById() method to get a transaction by its ID from the database.

//==============================================================================================

    @GetMapping
    public ResponseEntity<List<Transaction>> getALLTrascation(){
        return ResponseEntity.ok(new ArrayList<>());
    }
}
