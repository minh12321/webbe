package com.bongda.respository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bongda.model.HoaDon;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    
    List<HoaDon> findByMaKhachHang(String maKhachHang);

    void deleteByNgayMuaAndMaKhachHang(LocalDate ngayMua, String maKhachHang);
}
