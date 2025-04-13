package com.bongda.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bongda.model.HoaDon;
import com.bongda.service.HoaDonService;

@RestController
@RequestMapping("/shopbongda/api/hoadon")
public class HoaDonController {

    @Autowired
    private HoaDonService service;

    // Thêm mới hóa đơn
    @PostMapping
    public ResponseEntity<HoaDon> create(@RequestBody HoaDon hoaDon) {
        return ResponseEntity.ok(service.save(hoaDon));
    }

    // Lấy hóa đơn theo mã khách hàng
    @GetMapping("/makh/{maKH}")
    public ResponseEntity<List<HoaDon>> getByMaKhachHang(@PathVariable String maKH) {
        return ResponseEntity.ok(service.findByMaKhachHang(maKH));
    }

    // Lấy toàn bộ hóa đơn
    @GetMapping
    public ResponseEntity<List<HoaDon>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // Xóa theo ngày mua và mã khách hàng
    @DeleteMapping("/xoa")
    public ResponseEntity<String> deleteByNgayMuaVaMaKH(
            @RequestParam String maKH,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayMua
    ) {
        service.deleteByNgayMuaAndMaKhachHang(ngayMua, maKH);
        return ResponseEntity.ok("Đã xóa thành công");
    }
}
