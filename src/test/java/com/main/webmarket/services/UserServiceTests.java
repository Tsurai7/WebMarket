package com.main.webmarket.services;

import com.main.webmarket.entities.User;
import com.main.webmarket.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testAddUser_UserAlreadyExist_ShouldNotSaveUser() {
        User existingUser = new User();
        existingUser.setName("existingUser");

        User user = new User();
        user.setName("existingUser");

        when(userRepository.findByName(user.getName())).thenReturn(Optional.of(existingUser));

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.register(user));
    }
}
