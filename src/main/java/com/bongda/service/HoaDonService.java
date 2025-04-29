package com.bongda.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bongda.model.HoaDon;
import com.bongda.respository.HoaDonRepository;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository repo;

    public HoaDon save(HoaDon hoaDon) {
        return repo.save(hoaDon);
    }

    public List<HoaDon> findByMaKhachHang(String maKH) {
        return repo.findByMaKhachHang(maKH);
    }

    public List<HoaDon> findAll() {
        return repo.findAll();
    }

    public void deleteByNgayMuaAndMaKhachHang(LocalDate ngay, String maKH) {
        repo.deleteByNgayMuaAndMaKhachHang(ngay, maKH);
    }
    
    public HoaDon updateTrangThai(Long id, String trangThai) {
        Optional<HoaDon> optionalHoaDon = repo.findById(id);
        if (optionalHoaDon.isPresent()) {
            HoaDon hoaDon = optionalHoaDon.get();
            hoaDon.setTrangThai(trangThai);
            return repo.save(hoaDon);
        } else {
            throw new RuntimeException("Hóa đơn không tồn tại với id: " + id);
        }
    }
}
