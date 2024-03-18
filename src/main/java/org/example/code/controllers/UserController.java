package org.example.code.controllers;

import lombok.AllArgsConstructor;
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

    @PatchMapping("/addProduct")
    public ResponseEntity<User> addProductToUser(@RequestParam Long userId, @RequestParam Long productId) {
        return userService.addProductToUser(userId, productId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<User> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        return userService.removeProductFromUser(userId, productId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/getProductByTitle")
    public ResponseEntity<Product> getProductByTitle(@RequestParam Long userId, @RequestParam String title) {
        return userService.getProductById(userId, title) == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userService.getProductById(userId, title), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public User getById(@RequestParam Long id) {
        return userService.getById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return userService.register(user) ? new ResponseEntity<>(HttpStatus.CREATED) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestParam Long id, @RequestBody User user) {
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(@RequestParam Long id) {
        return userService.delete(id) ? new ResponseEntity<>(HttpStatus.OK) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}