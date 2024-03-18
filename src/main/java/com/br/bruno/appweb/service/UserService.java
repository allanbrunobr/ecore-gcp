package com.br.bruno.appweb.service;

import com.br.bruno.appweb.models.users.User;
import com.br.bruno.appweb.models.users.UserDTO;
import com.br.bruno.appweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        try {
            user.setPrimeiroNome(userDTO.getPrimeiroNome());
            user.setSegundoNome(userDTO.getSegundoNome());
            user.setEmail(userDTO.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

            userRepository.save(user);
        } catch (Exception e) {
        throw new RuntimeException("Failed to save user: " + e.getMessage());
    }
    }
}
