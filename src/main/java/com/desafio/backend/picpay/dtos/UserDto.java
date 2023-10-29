package com.desafio.backend.picpay.dtos;

import com.desafio.backend.picpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserDto(String firstName, String lastName, String document,
                      String email, String password, BigDecimal balance, UserType userType) {
}
