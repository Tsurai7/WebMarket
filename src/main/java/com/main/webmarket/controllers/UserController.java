package com.main.webmarket.controllers;

import com.main.webmarket.entities.Product;
import com.main.webmarket.entities.User;
import com.main.webmarket.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:MissingJavadocMethod"})
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

    /* @GetMapping("/getProductByTitle")
    public ResponseEntity<Product> getProductByTitle(
            @RequestParam Long userId, @RequestParam String title) {
        logger.info("Endpoint called: /api/users/getProductByTitle");
        return userService.getProductByTitle(userId, title) == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(userService.getProductByTitle(userId, title), HttpStatus.OK);
    } */

    @GetMapping("/getById")
    public User getById(@RequestParam Long id) {
        logger.info("Endpoint called: /api/users/getById");
        return userService.getById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        logger.info("Endpoint called: /api/users/register");
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
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
