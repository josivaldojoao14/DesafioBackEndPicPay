package com.desafio.backend.picpay.services;

import com.desafio.backend.picpay.domain.user.User;

import java.math.BigDecimal;

public interface AuthorizationService {
    boolean authorizeTransaction(User sender, BigDecimal amount);
}
