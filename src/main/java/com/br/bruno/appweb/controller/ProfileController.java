package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.models.User;
import com.br.bruno.appweb.models.UserDTO;
import com.br.bruno.appweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile/createUser")
    public String showCreatePage() {
        return "profile/createUser";
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> save(@RequestBody UserDTO userDTO) {
        var userCreated = userService.saveUser(userDTO);
        return generateResponse("Items saved successfully!", OK, userCreated);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<>(map,status);
    }
}
