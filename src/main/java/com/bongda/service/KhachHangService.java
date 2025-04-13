package com.bongda.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bongda.model.KhachHang;
import com.bongda.respository.KhachHangRepository;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository repo;

    public KhachHang capNhatChiTieu(String maKH, double chiTieuMoi) {
        KhachHang kh = repo.findByMaKhachHang(maKH)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        // Cập nhật chi tiêu cộng dồn
        kh.setChiTieu(kh.getChiTieu() + chiTieuMoi);

        // Kiểm tra giảm chi tiêu nếu quá 30 ngày
        if (Duration.between(kh.getLanCapNhat(), LocalDateTime.now()).toDays() >= 30) {
            kh.setChiTieu(kh.getChiTieu() / 2);
            kh.setLanCapNhat(LocalDateTime.now());
        }

        return repo.save(kh);
    }

    public List<KhachHang> getAll() {
        return repo.findAll();
    }

    public KhachHang getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public KhachHang save(KhachHang kh) {
        return repo.save(kh);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
