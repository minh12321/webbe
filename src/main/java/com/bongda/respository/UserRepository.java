package com.bongda.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bongda.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);  // Tìm người dùng theo tên đăng nhập
}
