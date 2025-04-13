package com.bongda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long accountId; // Mã tài khoản

    @Column(unique = true, nullable = false)
    private String username; // Tên đăng nhập (bắt buộc nếu không dùng đăng nhập xã hội)

    @Column(nullable = true)
    private String password; // Mật khẩu (nullable vì đăng nhập xã hội không cần mật khẩu)

    @Column(nullable = false)
    private String fullName; // Tên người dùng

    @Column(nullable = false)
    private String status; // Trạng thái tài khoản (ví dụ: "ACTIVE", "INACTIVE")

    @Column(nullable = false)
    private String accountType; // Loại tài khoản (ví dụ: "ADMIN", "USER", "SOCIAL")

    @Column(unique = true, nullable = true)
    private String email; // Email người dùng (nullable nếu không có email)

    @Column(nullable = true)
    private String provider; // Nhà cung cấp đăng nhập (ví dụ: "GOOGLE", "FACEBOOK")

    @Column(nullable = true, unique = true)
    private String providerId; // ID của người dùng trên Google/Facebook

    @Column(nullable = false)
    private int timelog;// số lần đăng nhập
    
    @Column(nullable = false)
    private LocalDateTime createdDate; // Ngày tạo tài khoản

    // Constructors
    public User() {
    }

    @PrePersist
    public void prePersist() {
        if (this.accountType == null) {
            this.accountType = "USER"; // Set default value before saving
        }
        if (this.status == null) {
            this.status = "ACTIVE"; // Set default account status
        }
        if (this.createdDate == null) {
            this.createdDate = LocalDateTime.now(); // Auto-set created date
        }
    }

    public int getTimelog() {
		return timelog;
	}

	public void setTimelog(int timelog) {
		this.timelog = timelog;
	}

	// Getters and Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
