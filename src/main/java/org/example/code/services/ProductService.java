package org.example.code.services;

import lombok.AllArgsConstructor;
import org.example.code.entities.Product;
import org.example.code.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productRepository.findAll();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<Product> getById(Long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }

        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Product> create(Product product) {

        Product createdProduct = productRepository.save(product);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    public ResponseEntity<Product> update(Long id, Product updatedProduct) {

        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setTitle(updatedProduct.getTitle());
            existingProduct.setDescription(updatedProduct.getDescription());

            Product updatedProductResult = productRepository.save(existingProduct);

            return new ResponseEntity<>(updatedProductResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Product> delete(Long id) {
        Product productToDelete = productRepository.findById(id).orElse(null);

        if (productToDelete != null) {
            productRepository.delete(productToDelete);
            return new ResponseEntity<>(productToDelete, HttpStatus.OK);
        }

        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
