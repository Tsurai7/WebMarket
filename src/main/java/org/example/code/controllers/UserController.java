package org.example.code.controllers;

import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.example.code.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/addProduct")
    public ResponseEntity<User> addProductToUser(@RequestParam Long userId, @RequestParam Long productId) {
        logger.info("Endpoint called: /api/users/addProduct");
        userService.addProductToUser(userId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<User> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        logger.info("Endpoint called: /api/users/removeProduct");
        userService.removeProductFromUser(userId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        logger.info("Endpoint called: /api/users/getAll");
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getProductByTitle")
    public ResponseEntity<Product> getProductByTitle(@RequestParam Long userId, @RequestParam String title) {
        logger.info("Endpoint called: /api/users/getProductByTitle");
        return userService.getProductById(userId, title) == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userService.getProductById(userId, title), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public User getById(@RequestParam Long id) {
        logger.info("Endpoint called: /api/users/getById");
        return userService.getById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        logger.info("Endpoint called: /api/users/register");
        return userService.register(user) ? new ResponseEntity<>(HttpStatus.CREATED) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}