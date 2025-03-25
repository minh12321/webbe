package com.bongda.respository;

import com.bongda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm người dùng theo tên đăng nhập
    User findByUsername(String username);

    // Tìm người dùng theo email
    User findByEmail(String email);

    // Tìm người dùng theo providerId (dùng để đăng nhập qua Google/Facebook)
    User findByProviderId(String providerId);

    // Tìm người dùng theo provider và providerId (dùng để phân biệt người dùng đăng nhập qua các mạng xã hội)
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
