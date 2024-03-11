package org.example.code.controllers;

import lombok.AllArgsConstructor;
import org.example.code.entities.Product;
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
        return productService.getAll();
    }

    @GetMapping("/getAll/advanced")
    public ResponseEntity<List<Product>> getAllAvanced(@RequestParam String category) {
        return productService.getAllAdvanced(category);
    }

    @GetMapping("/getById")
    public ResponseEntity<Product> getById(@RequestParam Long id) {
            return productService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> update(@RequestParam Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Product> delete(@RequestParam Long id) {
        return productService.delete(id);
    }
}