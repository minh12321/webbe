package com.bongda.model;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment ID
    private Long accountId;  // Mã tài khoản

    @Column(unique = true, nullable = false)
    private String username; // Tên đăng nhập

    @Column(nullable = false)
    private String password; // Mật khẩu đã mã hóa

    @Column(nullable = false)
    private String fullName; // Tên người dùng

    @Column(nullable = false)
    private String status; // Trạng thái tài khoản (ví dụ: "ACTIVE", "INACTIVE")
    
    @Column(nullable = false)
    private String accountType; // Loại tài khoản (ví dụ: "ADMIN", "USER")
    
    @Column(nullable = false)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdDate; // Ngày tạo tài khoản

    // Getters and Setters
    public User() {
    }

    @PrePersist
    public void prePersist() {
        if (this.accountType == null) {
            this.accountType = "GUEST"; // Set default value before saving
        }
    }
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
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
