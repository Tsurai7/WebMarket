package org.example.code.controllers;

import lombok.AllArgsConstructor;
import org.example.code.entities.User;
import org.example.code.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final AccountService accountService;

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return accountService.getAllUsers();
    }

    @PostMapping("/register")
    public User register(@RequestBody String name, @RequestBody String password) {
        return accountService.register(name, password);
    }
    
    @GetMapping("/login")
    public User login(@RequestBody String name, @RequestBody String password) {
        return accountService.login(name, password);
    }




}
