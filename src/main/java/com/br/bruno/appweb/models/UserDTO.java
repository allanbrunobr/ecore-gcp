package com.br.bruno.appweb.models;

import lombok.Data;

@Data
public class UserDTO {

    private String primeiroNome;
    private String segundoNome;
    private String email;
    private String password;
    private String cep;


}
