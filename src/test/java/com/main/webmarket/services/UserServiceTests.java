package com.main.webmarket.services;

import com.main.webmarket.entities.Product;
import com.main.webmarket.entities.User;
import com.main.webmarket.repositories.ProductRepository;
import com.main.webmarket.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void testRegister_UserAlreadyExists_ShouldThrowException() {
        User existingUser = new User();
        existingUser.setName("existingUser");
        existingUser.setPassword("password");

        when(userRepository.findByName(existingUser.getName())).thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.signUp(existingUser));
        assertEquals("User already exists", exception.getMessage());

        verify(userRepository, times(1)).findByName(existingUser.getName());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testAddProductToUser_UserExists_ProductExists() {
        Long userId = 1L;
        Long productId = 100L;

        User user = new User();
        user.setId(userId);

        Product product = new Product();
        product.setId(productId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        userService.addProductToUser(userId, productId);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertTrue(capturedUser.getProducts().contains(product));
    }

    @Test
    void addProductsToUser_Success() {
        Long userId = 1L;
        List<Long> productIds = Arrays.asList(101L, 102L, 103L);

        User user = new User();
        user.setId(userId);

        Product product1 = new Product();
        product1.setId(101L);
        Product product2 = new Product();
        product2.setId(102L);
        Product product3 = new Product();
        product3.setId(103L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(101L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(102L)).thenReturn(Optional.of(product2));
        when(productRepository.findById(103L)).thenReturn(Optional.of(product3));

        userService.addProductsToUser(userId, productIds);

        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(3)).findById(anyLong());
        verify(userRepository, times(1)).save(user);
        assertTrue(user.getProducts().contains(product1));
        assertTrue(user.getProducts().contains(product2));
        assertTrue(user.getProducts().contains(product3));
    }

    @Test
    void addProductsToUser_UserNotFound_ThrowsNoSuchElementException() {
        Long userId = 1L;
        List<Long> productIds = Arrays.asList(101L, 102L, 103L);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.addProductsToUser(userId, productIds));
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void addProductsToUser_ProductNotFound_ThrowsNoSuchElementException() {
        Long userId = 1L;
        List<Long> productIds = Arrays.asList(101L, 102L, 103L);

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.addProductsToUser(userId, productIds));
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(anyLong());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAddProductToUser_ProductNotFound() {
        Long userId = 1L;
        Long productId = 100L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> {
            userService.addProductToUser(userId, productId);
        });

        // Verify that userRepository.save() was not called
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRemoveProductFromUser_SuccessfullyRemoved() {
        Long userId = 1L;
        Long productId = 2L;

        User user = new User();
        user.setId(userId);

        Product product = new Product();
        product.setId(productId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        userService.removeProductFromUser(userId, productId);

        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        assertTrue(user.getProducts().isEmpty()); // Assuming removeProduct method updates user's product list
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRemoveProductFromUser_UserNotFound() {
        Long userId = 1L;
        Long productId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.removeProductFromUser(userId, productId));

        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRemoveProductFromUser_ProductNotFound() {
        Long userId = 1L;
        Long productId = 2L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.removeProductFromUser(userId, productId));

        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testGetById_UserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getById(userId);

        assertEquals(user, result);
    }

    @Test
    void testGetById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            userService.getById(userId);
        });
    }


    @Test
    void testUpdate_UserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("updatedUser");
        updatedUser.setPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> userService.update(userId, updatedUser));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void testDelete_UserExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDelete_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            userService.delete(userId);
        });

        verify(userRepository, never()).deleteById(userId);
    }
}
