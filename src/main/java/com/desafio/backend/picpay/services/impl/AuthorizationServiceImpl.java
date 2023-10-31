package com.desafio.backend.picpay.services.impl;

import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.services.AuthorizationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public boolean authorizeTransaction(User sender, BigDecimal amount) {
        return true;
    }
}
