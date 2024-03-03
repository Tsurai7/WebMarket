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

    @GetMapping("/getById/{id}")
    public User getById(@PathVariable Long id) {
        return accountService.getById(id);
    }

    @GetMapping("/login")
    public User login(@RequestBody User user) {
        return accountService.login(user);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return accountService.register(user);
    }

    @PutMapping("/update/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return accountService.update(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }
}