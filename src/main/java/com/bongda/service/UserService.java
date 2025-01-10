package com.bongda.service;

import com.bongda.model.User;
import com.bongda.respository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // Để mã hóa mật khẩu

    // Đăng ký người dùng mới
    public User registerUser(String username, String password, String fullName,String email) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));  // Mã hóa mật khẩu
        user.setFullName(fullName);
        user.setStatus("ACTIVE"); 
        user.setEmail(email);
        user.setCreatedDate(new Date());  // Ngày tạo tài khoản

        return userRepository.save(user);  // Lưu người dùng vào cơ sở dữ liệu
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;   
        }
        throw new RuntimeException("Invalid username or password");
    }
    public User updateUser(Long accountId, String fullName, String status,String email, String accountType) {
        Optional<User> userOptional = userRepository.findById(accountId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFullName(fullName);
            user.setStatus(status);
            user.setEmail(email);
            user.setAccountType(accountType);
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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
