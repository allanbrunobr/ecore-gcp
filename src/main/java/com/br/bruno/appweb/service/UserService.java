package com.br.bruno.appweb.service;

import com.br.bruno.appweb.models.users.User;
import com.br.bruno.appweb.models.users.UserDto;
//import com.br.bruno.appweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The `UserService` class is responsible for handling user-related operations,
 * such as saving user information.
 *
 * <p></p>Usage Example:
 *
 * <p>UserDTO userDTO = new UserDTO();
 * // Set userDTO properties
 *
 * <p>UserService userService = new UserService();
 * userService.saveUser(userDTO);
 */
@Service
@RequiredArgsConstructor
public class UserService {

  /**
   * Saves a user in the system.
   *
   * @param userDto The UserDto containing the user information to be saved.
   * @throws RuntimeException if there is an error saving the user.
   */
  public void saveUser(UserDto userDto) {
    User user = new User();
    try {
        // user.setPrimeiroNome(userDto.getPrimeiroNome());
        // user.setSegundoNome(userDto.getSegundoNome());
        // user.setEmail(userDto.getEmail());
        // user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        //    userRepository.save(user);
    } catch (Exception e) {
      throw new RuntimeException("Failed to save user: " + e.getMessage());
    }
  }
}
