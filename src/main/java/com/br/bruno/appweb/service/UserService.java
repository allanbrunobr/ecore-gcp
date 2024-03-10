package com.br.bruno.appweb.service;

import com.br.bruno.appweb.models.User;
import com.br.bruno.appweb.models.UserDTO;
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
    public User saveUser(UserDTO userDTO) {
        User user = new User();
        user.setPrimeiroNome(userDTO.getPrimeiroNome());
        user.setSegundoNome(userDTO.getSegundoNome());
        user.setEmail(userDTO.getEmail());
        String senhaCriptografada = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        user.setPassword(senhaCriptografada);
        user.setCep(userDTO.getCep());

        return userRepository.save(user);
    }
}
