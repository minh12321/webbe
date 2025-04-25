package com.bongda.service;

import com.bongda.model.User;
import com.bongda.respository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WithMockUser(username = "admin", roles = "ADMIN")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUser() {
        User user = new User();
        user.setAccountId(1L);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setStatus("ACTIVE");
        user.setAccountType("USER");
        user.setDiachi("123 Street");
        user.setTimelog(0);
        user.setCreatedDate(LocalDateTime.now());
        return user;
    }

    @Test
    void testRegisterUserSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User user = userService.registerUser("testuser", "password", "Test User", "test@example.com");

        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getFullName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserDuplicateUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(createUser());

        assertThrows(RuntimeException.class, () -> {
            userService.registerUser("testuser", "password", "Test User", "test@example.com");
        });
    }

    @Test
    void testLoginUserSuccess() {
        User user = createUser();
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.loginUser("testuser", "password");

        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void testLoginUserInvalidPassword() {
        User user = createUser();
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("wrong", user.getPassword())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            userService.loginUser("testuser", "wrong");
        });
        verify(userRepository).save(user);
        assertEquals(1, user.getTimelog());
    }

    @Test
    void testLoginUserMaxFailedAttempts() {
        User user = createUser();
        user.setTimelog(4);
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("wrong", user.getPassword())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            userService.loginUser("testuser", "wrong");
        });

        verify(userRepository).save(user);
        assertEquals("INACTIVE", user.getStatus());
    }

    @Test
    void testUpdateUserSuccess() {
        User user = createUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedNewPass");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User updated = userService.updateUser(1L, "New Name", "ACTIVE", "new@example.com", "ADMIN", "New Address", "newpass", "newuser");

        assertEquals("New Name", updated.getFullName());
        assertEquals("encodedNewPass", updated.getPassword());
        assertEquals("newuser", updated.getUsername());
    }

    @Test
    void testDeleteUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(1L);
        });
    }

    @Test
    void testGetUserById() {
        User user = createUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetUserByUsername() {
        User user = createUser();
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User result = userService.getUserByUsername("testuser");

        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetAllUsers() {
        User user = createUser();
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
    }

    @Test
    void testRegisterSocialUser_NewUser() {
        when(userRepository.findByProviderId("google123")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User user = userService.registerSocialUser("google", "google123", "Google User", "google@example.com");

        assertEquals("google", user.getProvider());
        assertEquals("google123", user.getProviderId());
        assertEquals("SOCIAL", user.getAccountType());
    }

    @Test
    void testRegisterSocialUser_ExistingUser() {
        User existing = createUser();
        when(userRepository.findByProviderId("google123")).thenReturn(existing);

        User result = userService.registerSocialUser("google", "google123", "Google User", "google@example.com");

        assertEquals(existing, result);
        verify(userRepository, never()).save(any(User.class));
    }
}
