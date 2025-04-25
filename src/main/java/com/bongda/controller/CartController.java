package com.bongda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bongda.model.CartItem;
import com.bongda.model.HoaDon;
import com.bongda.service.CartService;

@CrossOrigin(origins = "https://ab-mocha.vercel.app")
@RestController
@RequestMapping("/shopbongda/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<CartItem> addProduct(@RequestBody CartItem cartItem) {
        CartItem addedItem = cartService.addProductToCart(cartItem);
        return ResponseEntity.ok(addedItem);
    }
    @DeleteMapping("/remove/{productCode}")
    public ResponseEntity<Void> removeProduct(@PathVariable String productCode) {
        cartService.deleteById(productCode);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<CartItem>> getAll() {
        return ResponseEntity.ok(cartService.findAll());
    }
    @GetMapping("/gio/{productCode}")
    public ResponseEntity<List<CartItem>> getByMaKhachHang(@PathVariable String productCode) {
        return ResponseEntity.ok(cartService.findByMaKhachHang(productCode));
    }
}
