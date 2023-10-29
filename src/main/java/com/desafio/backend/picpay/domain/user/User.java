package com.desafio.backend.picpay.domain.user;

import com.desafio.backend.picpay.dtos.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDto userDto) {
        this.firstName = userDto.firstName();
        this.lastName = userDto.lastName();
        this.document = userDto.document();
        this.email = userDto.email();
        this.password = userDto.password();
        this.balance = userDto.balance();
        this.userType = userDto.userType();
    }

}
