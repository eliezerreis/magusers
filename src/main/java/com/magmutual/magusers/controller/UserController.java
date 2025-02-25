package com.magmutual.magusers.controller;

import com.magmutual.magusers.dto.UserDTO;
import com.magmutual.magusers.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;

/**
 * Provide the endpoints for the users microservices
 */
@RestController
@RequestMapping(value = "/v1/api/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<String> getInstanceInfo() throws UnknownHostException {
        String info = "This request was handled by instance: " + InetAddress.getLocalHost().getHostName();
        return ResponseEntity.ok(info) ;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
        UserDTO createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUser(@Valid @PathVariable int id) {
        UserDTO userDTO = userService.readUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO user) {
        userService.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-date")
    public ResponseEntity<List<UserDTO>> searchUserByDate(@RequestParam(required = true) LocalDate startDate,
                                                     @RequestParam(required = true) LocalDate endDate) {
        List<UserDTO> users = userService.findByCreatedDateBetween(startDate, endDate);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/by-profession")
    public ResponseEntity<List<UserDTO>> searchUsersByProfession(@RequestParam(required = true) String profession) {
        List<UserDTO> users = userService.findByProfession(profession);
        return ResponseEntity.ok(users);
    }

}
