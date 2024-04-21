package com.main.webmarket.controllers;

import com.main.webmarket.entities.User;
import com.main.webmarket.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/addProduct")
    public ResponseEntity<User> addProductToUser(
            @RequestParam Long userId, @RequestParam Long productId) {
        logger.info("Endpoint called: /api/users/addProduct");
        userService.addProductToUser(userId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addManyProducts")
    public ResponseEntity<String> addProductsToUser(@RequestParam Long userId, @RequestBody List<Long> products) {
        userService.addProductsToUser(userId, products);
        return ResponseEntity.status(HttpStatus.CREATED).body("Products added successfully");
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<User> removeProductFromCart(
            @RequestParam Long userId, @RequestParam Long productId) {
        logger.info("Endpoint called: /api/users/removeProduct");
        userService.removeProductFromUser(userId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        logger.info("Endpoint called: /api/users/getAll");
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public User getById(@RequestParam Long id) {
        return userService.getById(id);
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return new ResponseEntity<>(userService.signUp(user), HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<User> signIn(@RequestBody User user) {
        return Optional.ofNullable(userService.signIn(user))
                .map(loggedInUser -> ResponseEntity.ok(loggedInUser))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestParam Long id, @RequestBody User user) {
        logger.info("Endpoint called: /api/users/update");
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(@RequestParam Long id) {
        logger.info("Endpoint called: /api/users/delete");
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
