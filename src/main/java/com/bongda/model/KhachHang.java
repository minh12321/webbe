package com.bongda.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_khach_hang", unique = true)
    private String maKhachHang;

    private String tenKhachHang;

    private double chiTieu;

    private String thuHang;

    private LocalDateTime lanCapNhat; // để xử lý giảm 50% mỗi 30 ngày

    // Getters, setters...

    @PrePersist
    @PreUpdate
    public void capNhatThuHang() {
        this.thuHang = tinhThuHang();
        if (lanCapNhat == null) {
            lanCapNhat = LocalDateTime.now();
        }
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public double getChiTieu() {
		return chiTieu;
	}

	public void setChiTieu(double chiTieu) {
		this.chiTieu = chiTieu;
	}

	public String getThuHang() {
		return thuHang;
	}

	public void setThuHang(String thuHang) {
		this.thuHang = thuHang;
	}

	public LocalDateTime getLanCapNhat() {
		return lanCapNhat;
	}

	public void setLanCapNhat(LocalDateTime lanCapNhat) {
		this.lanCapNhat = lanCapNhat;
	}

	private String tinhThuHang() {
        if (chiTieu >= 10_000_000) return "Kim cương";
        else if (chiTieu >= 1_000_000) return "Vàng";
        else if (chiTieu >= 500_000) return "Bạc";
        else if (chiTieu >= 100_000) return "Đồng";
        else return "Chưa xếp hạng";
    }
}
