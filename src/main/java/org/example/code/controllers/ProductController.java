package org.example.code.controllers;

import lombok.AllArgsConstructor;
import org.example.code.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.code.services.ProductService;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController
{
    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<Product> getById(@RequestParam Long id) {
            return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return new ResponseEntity<>(productService.create(product), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> update(@RequestParam Long id, @RequestBody Product product) {
        return productService.update(id, product) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Product> delete(@RequestParam Long id) {
        return productService.delete(id) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}