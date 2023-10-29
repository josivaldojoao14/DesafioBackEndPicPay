package com.desafio.backend.picpay.dtos;

import java.math.BigDecimal;

public record TransactionDto(BigDecimal amount, Long senderId, Long receiverId) {
}
