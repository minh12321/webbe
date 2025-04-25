package com.bongda.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bongda.model.CartItem;
import com.bongda.model.HoaDon;
import com.bongda.respository.CartItemRepository;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartItem addProductToCart(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

//    public void removeProductFromCart(String productCode) {
//        cartItemRepository.deleteById(productCode);
//    }
    
    @Transactional
    public void deleteById(String productCode) {
        cartItemRepository.deleteByProductCode(productCode);
    }

//    public CartItem getCartItem(String productCode) {
//        return cartItemRepository.findByProductCode(productCode).orElse(null);
//    }
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }
    
    public List<CartItem> findByMaKhachHang(String maKH) {
        return cartItemRepository.findByOwner(maKH);
    }

}
