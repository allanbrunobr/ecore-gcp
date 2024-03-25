package com.br.bruno.appweb.controller;

import static org.springframework.http.HttpStatus.OK;

import com.br.bruno.appweb.models.users.UserDto;
import com.br.bruno.appweb.service.UserService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class represents the controller for user profiles. It handles the HTTP requests related
 * to creating and managing user profiles.
 */
@RestController
@RequiredArgsConstructor
public class ProfileController {

  private final UserService userService;

  @PostMapping("/create")
  public ResponseEntity<Object> save(@RequestBody UserDto userDto) {
    userService.saveUser(userDto);
    return generateResponse("Items saved successfully!", OK, userDto);
  }

  /**
   * Generates a response entity with the given message, HTTP status, and response object.
   *
   * @param message The message to be included in the response.
   * @param status  The HTTP status of the response.
   * @param responseObj The object to be included in the response.
   * @return A ResponseEntity object with the generated response.
   */
  public static ResponseEntity<Object> generateResponse(String message,
                                                        HttpStatus status,
                                                        Object responseObj) {
    Map<String, Object> map = new HashMap<>();
    map.put("message", message);
    map.put("status", status.value());
    map.put("data", responseObj);

    return new ResponseEntity<>(map, status);
  }
}
