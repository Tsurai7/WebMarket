package com.main.webmarket.services;


import com.main.webmarket.entities.Product;
import com.main.webmarket.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final RequestCounterService requestCounterService;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found with id: %d";

    @Autowired
    public ProductService(ProductRepository productRepository, RequestCounterService requestCounterService) {
        this.productRepository = productRepository;
        this.requestCounterService = requestCounterService;
    }

    public List<Product> getAll() {
        requestCounterService.incrementRequestsCount();
        return productRepository.findAll();
    }


    public Product getById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() ->
                new NoSuchElementException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id)));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }


    public void update(Long id, Product updatedProduct) {
        productRepository.findById(id)
            .map(existingProduct -> {
                existingProduct.setTitle(updatedProduct.getTitle());
                existingProduct.setImage(updatedProduct.getImage());
                existingProduct.setCategory(updatedProduct.getCategory());
                existingProduct.setBrand(updatedProduct.getBrand());
                existingProduct.setDescription(updatedProduct.getDescription());
                return productRepository.save(existingProduct);
            })
            .orElseThrow(() ->
                new NoSuchElementException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id)));
    }


    public void delete(Long id) {
        productRepository.findById(id)
            .ifPresentOrElse(
                productRepository::delete,
                () -> {
                    throw new NoSuchElementException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
                }
            );
    }
}
