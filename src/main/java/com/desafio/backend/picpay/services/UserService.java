package com.desafio.backend.picpay.services;

import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.dtos.UserDto;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    void validateTransaction(User sender, BigDecimal amount) throws Exception;
    User createUser(UserDto userDto);
    User findUserById(Long id) throws Exception;
    void saveUser(User user);
    List<User> getAllUsers();

}
