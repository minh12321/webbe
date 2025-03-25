package com.bongda.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Entity
@Table(name = "SanPham")
public class Product {

    @Id
    private String maSanPham; 

    @Column(nullable = false)
    private String tenSanPham;

    @Column(nullable = false)
    private double gia;

    @Column(nullable = false)
    private int soLuong;

    @Column(nullable = false)
    private int maDanhMuc;
    
    @Column(columnDefinition = "TEXT")
    private String moTa;
    
    @Column(nullable = false)
    private String mauSac; 

    @Column(nullable = false)
    private String size;

    @Column(nullable = true)
    private String hinhAnh;  // Lưu đường dẫn ảnh thay vì byte[]

    private static final AtomicInteger counter = new AtomicInteger(1);

    public static String generateProductId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return timestamp + String.format("%03d", counter.getAndIncrement());
    }

    @PrePersist
    public void prePersist() {
        this.maSanPham = generateProductId();
    }

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public double getGia() {
		return gia;
	}

	public void setGia(double gia) {
		this.gia = gia;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public int getMaDanhMuc() {
		return maDanhMuc;
	}

	public void setMaDanhMuc(int maDanhMuc) {
		this.maDanhMuc = maDanhMuc;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getMauSac() {
		return mauSac;
	}

	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public static AtomicInteger getCounter() {
		return counter;
	}
    
}
