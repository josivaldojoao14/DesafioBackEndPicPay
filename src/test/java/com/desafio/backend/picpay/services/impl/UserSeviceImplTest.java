package com.desafio.backend.picpay.services.impl;

import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.domain.user.UserType;
import com.desafio.backend.picpay.dtos.TransactionDto;
import com.desafio.backend.picpay.dtos.UserDto;
import com.desafio.backend.picpay.repositories.UserRepository;
import com.desafio.backend.picpay.services.TransactionService;
import com.desafio.backend.picpay.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserSeviceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserSeviceImpl();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateTransactionCase1() throws Exception {
        User sender = new User(1L, "Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(100), UserType.MERCHANT);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            userService.validateTransaction(sender, new BigDecimal(10));
        });

        Assertions.assertEquals("Usuários lojistas não podem realizar esse tipo de transação.", thrown.getMessage());
    }

    @Test
    void validateTransactionCase2() throws Exception {
        User sender = new User(1L, "Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(0), UserType.COMMON);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            userService.validateTransaction(sender, new BigDecimal(10));
        });

        Assertions.assertEquals("Saldo insuficiente para realizar essa transação.", thrown.getMessage());
    }

    @Test
    void createUser() {
        User newUser = new User(1L, "Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(100), UserType.COMMON);
        UserDto userMapped = new UserDto("Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(100), UserType.COMMON);

        given(userRepository.save(newUser)).willReturn(newUser);
        User userSaved = userService.createUser(userMapped);

        assertThat(userSaved).isNotNull();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findUserById() throws Exception {
        User newUser = new User(1L, "Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(100), UserType.COMMON);

        given(userRepository.findById(1L)).willReturn(Optional.of(newUser));

        User expected = userService.findUserById(1L);

        assertThat(expected).isNotNull();
    }

    @Test
    void saveUser() {
        User newUser = new User(1L, "Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(100), UserType.COMMON);

        given(userRepository.save(newUser)).willReturn(newUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers() {
        User sender = new User(1L, "Joao", "Pereira", "9999999901",
                "joao@gmail.com", "123456", new BigDecimal(100), UserType.COMMON);
        User receiver = new User(2L, "Maria", "Silva", "9999999902",
                "maria@gmail.com", "123456", new BigDecimal(50), UserType.COMMON);
        List<User> datas = List.of(sender, receiver);

        given(userRepository.findAll()).willReturn(datas);
        List<User> expected = userService.getAllUsers();

        assertEquals(expected, datas);
    }
}