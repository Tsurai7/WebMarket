package com.main.webmarket.services;

import com.main.webmarket.entities.Product;
import com.main.webmarket.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetById_ProductExists_ShouldReturnProduct() {
        Long productId = 1L;
        Product expectedProduct = new Product();
        expectedProduct.setId(productId);
        expectedProduct.setTitle("TestProduct");

        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        Product result = productService.getById(productId);

        verify(productRepository, times(1)).findById(productId);
        assertEquals(expectedProduct, result);
    }

    @Test
    void testGetById_ProductDoesNotExist_ShouldThrowException() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.getById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testUpdate_ProductExists_ShouldUpdateProduct() {
        Long productId = 1L;
        String updatedTitle = "Updated Title";
        String updatedDescription = "Updated Description";
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setTitle("Original Title");
        existingProduct.setDescription("Original Description");

        Product updatedProduct = new Product();
        updatedProduct.setTitle(updatedTitle);
        updatedProduct.setDescription(updatedDescription);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        productService.update(productId, updatedProduct);

        assertEquals(updatedTitle, existingProduct.getTitle());
        assertEquals(updatedDescription, existingProduct.getDescription());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdate_ProductDoesNotExist_ShouldThrowException() {
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setTitle("Updated Title");
        updatedProduct.setDescription("Updated Description");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.update(productId, updatedProduct));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDelete_ProductExists_ShouldDeleteProduct() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.delete(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(existingProduct);
    }

    @Test
    void testDelete_ProductDoesNotExist_ShouldThrowException() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.delete(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any());
    }
}
