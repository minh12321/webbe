package com.bongda.service;

import com.bongda.model.User;
import com.bongda.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // Add authorities/roles if needed
        );
    }

    public User registerUser(String username, String password, String fullName, String email) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setStatus("ACTIVE");
        user.setEmail(email);
        user.setCreatedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Username or password is incorrect");
        }

        if ("INACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            int log = user.getTimelog() + 1;
            user.setTimelog(log);

            if (log >= 5) {
                user.setStatus("INACTIVE");
            }

            userRepository.save(user);
            throw new RuntimeException("Username or password is incorrect");
        }

        user.setTimelog(0);
        userRepository.save(user);
        return user;
    }

    public User updateUser(Long accountId, String fullName, String status, String accountType, String email, String diachi, String password, String username) {
        Optional<User> userOptional = userRepository.findById(accountId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFullName(fullName);
            user.setStatus(status);
            user.setEmail(email);
            user.setAccountType(accountType);
            user.setDiachi(diachi);
            if (password != null && !password.trim().isEmpty()) {
                user.setPassword(passwordEncoder.encode(password));
            }
            user.setUsername(username);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(Long accountId) {
        if (userRepository.existsById(accountId)) {
            userRepository.deleteById(accountId);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User getUserById(Long accountId) {
        return userRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerSocialUser(String provider, String providerId, String fullName, String email) {
        User existingUser = userRepository.findByProviderId(providerId);
        if (existingUser != null) {
            return existingUser;
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setProvider(provider);
        user.setProviderId(providerId);
        user.setStatus("ACTIVE");
        user.setAccountType("SOCIAL");
        user.setCreatedDate(LocalDateTime.now());

        return userRepository.save(user);
    }
}