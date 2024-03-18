package com.br.bruno.appweb.models.users;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "ec_user")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "primeiro_nome")
    private String primeiroNome;
    @Column(name = "segundo_nome")
    private String segundoNome;
    private String email;
    private String password;
}
