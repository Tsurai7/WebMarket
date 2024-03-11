package org.example.code.controllers;

import lombok.AllArgsConstructor;
import org.example.code.entities.BankCard;
import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.example.code.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/addProduct")
    public ResponseEntity<User> addProductToUser(@RequestParam Long userId, @RequestParam Long productId) {
        return userService.addProductToUser(userId, productId);
    }

    @PostMapping("/addCard")
    public ResponseEntity<User> addCard(@RequestParam Long userId, @RequestBody BankCard card) {
        return userService.addCard(userId, card);
    }

    @DeleteMapping("/removeCard")
    public ResponseEntity<User> removeCard(@RequestParam Long id) {
        return userService.removeCard(id);
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getProductById")
    public ResponseEntity<Product> getProductById(@RequestParam Long userId, @RequestParam Long productId) {
        return userService.getProductById(userId, productId);
    }


    @GetMapping("/getById")
    public User getById(@RequestParam Long id) {
        return userService.getById(id);
    }

    @GetMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping("/update")
    public User update(@RequestParam Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        userService.delete(id);
    }
}