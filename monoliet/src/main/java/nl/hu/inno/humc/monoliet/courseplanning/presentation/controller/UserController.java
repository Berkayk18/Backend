package nl.hu.inno.humc.monoliet.courseplanning.presentation.controller;

import nl.hu.inno.humc.monoliet.courseplanning.application.UserService;
import nl.hu.inno.humc.monoliet.courseplanning.presentation.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> CreateUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO.getUsername(), userDTO.getEmail());
        return ResponseEntity.ok("new user created!");
    }
}
