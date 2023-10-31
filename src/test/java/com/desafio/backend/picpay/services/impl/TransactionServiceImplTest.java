package com.desafio.backend.picpay.services.impl;

import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.domain.user.UserType;
import com.desafio.backend.picpay.dtos.TransactionDto;
import com.desafio.backend.picpay.repositories.TransactionRepository;
import com.desafio.backend.picpay.services.AuthorizationService;
import com.desafio.backend.picpay.services.NotificationService;
import com.desafio.backend.picpay.services.TransactionService;
import com.desafio.backend.picpay.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {
    @Mock
    private UserService userSevice;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private TransactionService transactionService = new TransactionServiceImpl();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception {
        User sender = new User(1L, "Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(100), UserType.COMMON);
        User receiver = new User(2L, "Maria", "Silva", "9999999902",
                "maria@gmail.com", "123456", new BigDecimal(50), UserType.COMMON);

        when(userSevice.findUserById(1L)).thenReturn(sender);
        when(userSevice.findUserById(2L)).thenReturn(receiver);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDto request = new TransactionDto(new BigDecimal(10), sender.getId(), receiver.getId());
        transactionService.createTransaction(request);

        verify(transactionRepository, times(1)).save(any());

        sender.setBalance(new BigDecimal(90));
        verify(userSevice, times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(60));
        verify(userSevice, times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender,
                "Transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotification(receiver,
                "Transação recebida com sucesso");
    }

    @Test
    @DisplayName("Should throw Exception when transaction is not allowed")
    void createTransactionCase2() throws Exception {
        User sender = new User(1L, "Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(100), UserType.COMMON);
        User receiver = new User(2L, "Maria", "Silva", "9999999902",
                "maria@gmail.com", "123456", new BigDecimal(50), UserType.COMMON);

        when(userSevice.findUserById(1L)).thenReturn(sender);
        when(userSevice.findUserById(2L)).thenReturn(receiver);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDto request = new TransactionDto(new BigDecimal(10), sender.getId(), receiver.getId());
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transação não autorizada!", thrown.getMessage());
    }
}