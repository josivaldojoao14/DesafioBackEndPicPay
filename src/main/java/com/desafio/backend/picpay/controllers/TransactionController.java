package com.desafio.backend.picpay.controllers;

import com.desafio.backend.picpay.domain.transaction.Transaction;
import com.desafio.backend.picpay.dtos.TransactionDto;
import com.desafio.backend.picpay.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transactionDto) throws Exception {
        Transaction newTransaction = this.transactionService.createTransaction(transactionDto);
        return new ResponseEntity<Transaction>(newTransaction, HttpStatus.CREATED);
    }
}
