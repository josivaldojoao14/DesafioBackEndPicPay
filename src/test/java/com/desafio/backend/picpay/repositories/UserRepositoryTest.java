package com.desafio.backend.picpay.repositories;

import com.desafio.backend.picpay.domain.user.User;
import com.desafio.backend.picpay.domain.user.UserType;
import com.desafio.backend.picpay.dtos.UserDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from db")
    void findUserByDocumentCase1() {
        String document = "99999999901";
        UserDto data = new UserDto("Joao", "Castro", document, "joao@gmail.com",
                "123456", new BigDecimal(10), UserType.COMMON);
        this.createUser(data);

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from DB successfully when user not exist")
    void findUserByDocumentCase2() {
        String document = "99999999901";

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should get user successfully from db")
    void findUserByEmailCase1() {
        String document = "99999999901";
        String email = "joao@gmail.com";
        UserDto data = new UserDto("Joao", "Castro", document, email,
                "123456", new BigDecimal(10), UserType.COMMON);
        this.createUser(data);

        Optional<User> result = this.userRepository.findUserByEmail(email);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from DB successfully when user not exist")
    void findUserByEmailCase2() {
        String email = "joao@gmail.com";

        Optional<User> result = this.userRepository.findUserByEmail(email);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should get user successfully from db")
    void findUserByIdCase1() {
        String document = "99999999901";
        String email = "joao@gmail.com";
        UserDto data = new UserDto("Joao", "Castro", document, email,
                "123456", new BigDecimal(10), UserType.COMMON);
        User usercreated = this.createUser(data);

        Optional<User> result = this.userRepository.findUserById(usercreated.getId());

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from DB successfully when user not exist")
    void findUserByIdCase2() {
        Long id = 1L;

        Optional<User> result = this.userRepository.findUserById(id);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserDto data) {
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}