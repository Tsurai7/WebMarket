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
    void testRegister_UserDoesNotExist_ShouldSaveUser() {
        // Arrange
        User newUser = new User();
        newUser.setName("testUser");
        newUser.setPassword("password");

        when(userRepository.findByName(newUser.getName())).thenReturn(Optional.empty());

        // Act
        User result = userService.register(newUser);

        verify(userRepository, times(1)).findByName(newUser.getName());

        assertEquals(newUser.getName(), result.getName());
        assertEquals(newUser.getPassword(), result.getPassword());
    }

    @Test
    void testRegister_UserAlreadyExists_ShouldThrowException() {
        // Arrange
        User existingUser = new User();
        existingUser.setName("existingUser");
        existingUser.setPassword("password");

        when(userRepository.findByName(existingUser.getName())).thenReturn(Optional.of(existingUser));

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.register(existingUser));
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

        // Act
        userService.addProductToUser(userId, productId);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertTrue(capturedUser.getProducts().contains(product));
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
        // Arrange
        Long userId = 1L;
        Long productId = 2L;

        User user = new User();
        user.setId(userId);

        Product product = new Product();
        product.setId(productId);

        // Stubbing repository methods
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        userService.removeProductFromUser(userId, productId);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        assertTrue(user.getProducts().isEmpty()); // Assuming removeProduct method updates user's product list
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRemoveProductFromUser_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;

        // Stubbing repository methods
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.removeProductFromUser(userId, productId));

        // Verify interactions
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRemoveProductFromUser_ProductNotFound() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;

        User user = new User();
        user.setId(userId);

        // Stubbing repository methods
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.removeProductFromUser(userId, productId));

        // Verify interactions
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

        // Act
        User result = userService.getById(userId);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> {
            userService.getById(userId);
        });
    }

    @Test
    void testGetAll() {
        List<User> users = Arrays.asList(
                new User("user1", "password1"),
                new User("user2", "password2"),
                new User("user3", "password3")
        );

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAll();

        // Assert
        assertEquals(users.size(), result.size());
        Assertions.assertIterableEquals(users, result);
    }

    @Test
    void testUpdate_UserDoesNotExist_ShouldThrowException() {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("updatedUser");
        updatedUser.setPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.update(userId, updatedUser));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void testDelete_UserExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDelete_UserNotFound() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> {
            userService.delete(userId);
        });

        // Verify that userRepository.deleteById() was not called
        verify(userRepository, never()).deleteById(userId);
    }
}
