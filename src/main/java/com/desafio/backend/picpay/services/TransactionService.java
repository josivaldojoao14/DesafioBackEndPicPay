package com.desafio.backend.picpay.services;

import com.desafio.backend.picpay.domain.transaction.Transaction;
import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.dtos.TransactionDto;
import com.desafio.backend.picpay.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserSevice userSevice;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDto transactionDto) throws Exception {
        User sender = this.userSevice.findUserById(transactionDto.senderId());
        User receiver = this.userSevice.findUserById(transactionDto.receiverId());

        userSevice.validateTransaction(sender, transactionDto.amount());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDto.amount());
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

    public boolean authorizeTransaction(User sender, BigDecimal amount){
        return true;
//       ResponseEntity<Map> authResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
//
//       if(authResponse.getStatusCode() == HttpStatus.OK && authResponse.getBody().get("message") == "AUTORIZADO"){
//           return true;
//       } else return false;
    }
}
