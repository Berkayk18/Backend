package nl.hu.inno.humc.courseplanning.presentation.controller;

import nl.hu.inno.humc.courseplanning.application.service.UserService;
import nl.hu.inno.humc.courseplanning.presentation.dto.UserDTO;
import nl.hu.inno.humc.courseplanning.presentation.dto.UserInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInformation(@PathVariable UUID userId) {
        UserInfoDTO userInfoDTO = userService.getUserInformation(userId);
        return ResponseEntity.ok(userInfoDTO);
    }
}
