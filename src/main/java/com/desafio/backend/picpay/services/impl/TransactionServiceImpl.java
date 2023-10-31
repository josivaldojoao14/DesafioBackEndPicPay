package com.desafio.backend.picpay.services.impl;

import com.desafio.backend.picpay.domain.transaction.Transaction;
import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.dtos.TransactionDto;
import com.desafio.backend.picpay.repositories.TransactionRepository;
import com.desafio.backend.picpay.services.AuthorizationService;
import com.desafio.backend.picpay.services.NotificationService;
import com.desafio.backend.picpay.services.TransactionService;
import com.desafio.backend.picpay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private UserService userSevice;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthorizationService authorizationService;

    public Transaction createTransaction(TransactionDto transactionDto) throws Exception {
        User sender = this.userSevice.findUserById(transactionDto.senderId());
        User receiver = this.userSevice.findUserById(transactionDto.receiverId());

        userSevice.validateTransaction(sender, transactionDto.amount());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(sender, transactionDto.amount());
        if(!isAuthorized){
            throw new Exception("Transação não autorizada!");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.amount());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDto.amount()));
        receiver.setBalance(receiver.getBalance().add(transactionDto.amount()));

        this.transactionRepository.save(transaction);

        this.userSevice.saveUser(sender);
        this.userSevice.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return transaction;
    }
}
