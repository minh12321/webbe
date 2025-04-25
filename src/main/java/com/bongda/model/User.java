package com.bongda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long accountId; 

    @Column(unique = true, nullable = false)
    private String username; 

    @Column(nullable = true)
    private String password; 

    @Column(nullable = false)
    private String fullName; 

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String accountType; 

    @Column(unique = true, nullable = true)
    private String email; 

    @Column(nullable = true)
    private String provider; 

    @Column(nullable = true, unique = true)
    private String providerId; 

    @Column(nullable = false)
    private int timelog;
    
    @Column(nullable = false)
    private LocalDateTime createdDate;
    
    @Column(nullable = true)
    private String diachi; 
    
    // Constructors
    public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	
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
