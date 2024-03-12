package org.example.code.services;

import lombok.AllArgsConstructor;
import org.example.code.entities.Product;
import org.example.code.repositories.ProductRepository;
import org.example.code.utilities.CustomCache;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CustomCache customCache;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public boolean update(Long id, Product updatedProduct) {

        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setTitle(updatedProduct.getTitle());
            existingProduct.setDescription(updatedProduct.getDescription());

            Product updatedProductResult = productRepository.save(existingProduct);
            customCache.addToCache(updatedProductResult.getId().toString(), updatedProductResult);

            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        Product productToDelete = productRepository.findById(id).orElse(null);

        if (productToDelete != null) {
            productRepository.delete(productToDelete);

            return true;
        }

        else {
            return false;
        }
    }
}
