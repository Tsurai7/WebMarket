package org.example.code.controllers;

import lombok.AllArgsConstructor;
import org.example.code.entities.BankCard;
import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.example.code.services.UserService;
import org.springframework.http.HttpStatus;
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
        return userService.addProductToUser(userId, productId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<User> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        return userService.removeProductFromUser(userId, productId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addCard")
    public ResponseEntity<User> addCard(@RequestParam Long userId, @RequestBody BankCard card) {
        return userService.addCard(userId, card) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/removeCard")
    public ResponseEntity<User> removeCard(@RequestParam Long userId, @RequestParam Long cardId) {
       return userService.removeCard(userId, cardId) ? new ResponseEntity<>(HttpStatus.OK) :
               new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/getProductById")
    public ResponseEntity<Product> getProductById(@RequestParam Long userId, @RequestParam Long productId) {
        return new ResponseEntity<>(userService.getProductById(userId, productId), HttpStatus.OK);
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
    public ResponseEntity<User> register(@RequestBody User user) {
        return userService.register(user) ? new ResponseEntity<>(HttpStatus.CREATED) :
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestParam Long id, @RequestBody User user) {
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(@RequestParam Long id) {
        return userService.delete(id) ? new ResponseEntity<>(HttpStatus.OK) :
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}