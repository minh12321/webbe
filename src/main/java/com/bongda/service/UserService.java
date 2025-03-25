package com.bongda.service;

import com.bongda.model.User;
import com.bongda.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Để mã hóa mật khẩu

    // Đăng ký người dùng mới
    public User registerUser(String username, String password, String fullName, String email) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu
        user.setFullName(fullName);
        user.setStatus("ACTIVE");
        user.setEmail(email);
        user.setCreatedDate(LocalDateTime.now()); // Ngày tạo tài khoản

        return userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
    }

    // Đăng nhập người dùng
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Invalid username or password");
    }

    // Cập nhật thông tin người dùng
    public User updateUser(Long accountId, String fullName, String status, String email, String accountType) {
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

    // Xóa người dùng
    public void deleteUser(Long accountId) {
        if (userRepository.existsById(accountId)) {
            userRepository.deleteById(accountId);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Lấy thông tin người dùng theo ID
    public User getUserById(Long accountId) {
        return userRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Đăng ký người dùng thông qua Google/Facebook
    public User registerSocialUser(String provider, String providerId, String fullName, String email) {
        User existingUser = userRepository.findByProviderId(providerId);
        if (existingUser != null) {
            // Nếu người dùng đã đăng ký qua Google/Facebook trước đó, chỉ cần trả về người dùng đã tồn tại
            return existingUser;
        }

        // Nếu chưa có người dùng, tạo người dùng mới
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setProvider(provider);  // Lưu tên provider (Google/Facebook)
        user.setProviderId(providerId); // Lưu ID từ provider
        user.setStatus("ACTIVE");
        user.setAccountType("SOCIAL");  // Loại tài khoản là "SOCIAL"
        user.setCreatedDate(LocalDateTime.now());  // Ngày tạo tài khoản

        return userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
    }
}
