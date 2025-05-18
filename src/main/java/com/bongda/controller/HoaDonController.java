package com.bongda.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bongda.model.HoaDon;
import com.bongda.service.HoaDonService;
import com.bongda.websocket.MyWebSocketHandler;

@CrossOrigin(origins = "https://ab-mocha.vercel.app")
@RestController
@RequestMapping("/shopbongda/api/hoadon")
public class HoaDonController {

    @Autowired
    private HoaDonService service;
    

    
    @Autowired
    private MyWebSocketHandler socketHandler;

    @PostMapping
    public ResponseEntity<HoaDon> create(@RequestBody HoaDon hoaDon) {
    	HoaDon saved = service.save(hoaDon);

        // Gửi WebSocket message
        socketHandler.broadcast(saved);
    	 
        return ResponseEntity.ok(service.save(hoaDon));
    }

    @GetMapping("/makh/{maKH}")
    public ResponseEntity<List<HoaDon>> getByMaKhachHang(@PathVariable String maKH) {
        return ResponseEntity.ok(service.findByMaKhachHang(maKH));
    }

    @GetMapping
    public ResponseEntity<List<HoaDon>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/xoa")
    public ResponseEntity<String> deleteByNgayMuaVaMaKH(
            @RequestParam String maKH,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayMua
    ) {
        service.deleteByNgayMuaAndMaKhachHang(ngayMua, maKH);
        return ResponseEntity.ok("Đã xóa thành công");
    }
    
    @PutMapping("/{id}/trangthai")
    public HoaDon updateTrangThai(@PathVariable Long id, @RequestParam String trangThai) {
        return service.updateTrangThai(id, trangThai);
    }
}
