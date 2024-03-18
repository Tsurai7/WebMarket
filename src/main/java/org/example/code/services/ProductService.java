package org.example.code.services;

import org.example.code.entities.Product;
import org.example.code.repositories.ProductRepository;
import org.example.code.utilities.CustomCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final CustomCache customCache;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found with id: %d";

    @Autowired
    public ProductService(ProductRepository productRepository, CustomCache customCache) {
        this.productRepository = productRepository;
        this.customCache = customCache;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }


    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            logger.error(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
            throw  new NoSuchElementException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
        });
    }


    public Product create(Product product) {
        return productRepository.save(product);
    }


    public void update(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> {
            logger.error(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
            throw  new NoSuchElementException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
        });

        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setDescription(updatedProduct.getDescription());

        Product updatedProductResult = productRepository.save(existingProduct);
        customCache.addToCache(updatedProductResult.getId().toString(), updatedProductResult);
    }


    public void delete(Long id) {
        Product productToDelete = productRepository.findById(id).orElseThrow(() -> {
            logger.error(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
            throw  new NoSuchElementException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
        });

        productRepository.delete(productToDelete);
    }
}