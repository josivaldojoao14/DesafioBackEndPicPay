package com.desafio.backend.picpay.services;

import com.desafio.backend.picpay.domain.transaction.Transaction;
import com.desafio.backend.picpay.dtos.TransactionDto;

public interface TransactionService {
    Transaction createTransaction(TransactionDto transactionDto) throws Exception;
}
