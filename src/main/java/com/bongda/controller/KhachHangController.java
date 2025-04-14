package com.bongda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bongda.model.KhachHang;
import com.bongda.service.KhachHangService;

@CrossOrigin(origins = "https://ab-mocha.vercel.app")
@RestController
@RequestMapping("/shopbongda/api/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangService service;

    @PostMapping("/capnhat")
    public ResponseEntity<KhachHang> capNhatChiTieu(@RequestParam String maKH, @RequestParam double chiTieuMoi) {
        return ResponseEntity.ok(service.capNhatChiTieu(maKH, chiTieuMoi));
    }

    @GetMapping
    public List<KhachHang> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public KhachHang getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public KhachHang create(@RequestBody KhachHang kh) {
        return service.save(kh);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
