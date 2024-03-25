package com.br.bruno.appweb.models.users;

import lombok.Data;

/**
 * UserDTO is a data transfer object that represents a user. It is mainly used for transferring user
 * data between different layers of the application.
 *
 * @Data annotation is used from the Lombok library to generate getter and setter methods for the
 * fields of UserDTO class.
 */
@Data
public class UserDto {

  private String primeiroNome;
  private String segundoNome;
  private String email;
  private String password;
}
