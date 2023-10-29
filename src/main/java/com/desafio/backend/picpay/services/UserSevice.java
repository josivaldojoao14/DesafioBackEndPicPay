package com.desafio.backend.picpay.services;

import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.domain.user.UserType;
import com.desafio.backend.picpay.dtos.UserDto;
import com.desafio.backend.picpay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserSevice {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuários lojistas não podem realizar esse tipo de transação.");
        }

        if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente para realizar essa transação.");
        }
    }

    public User createUser(UserDto userDto){
        User newUser = new User(userDto);
        this.saveUser(newUser);
        return newUser;
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
