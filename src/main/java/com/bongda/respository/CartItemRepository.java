package com.bongda.respository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bongda.model.CartItem;
import com.bongda.model.HoaDon;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByOwner(String owner);
	List<CartItem> findByProductCode(String productCode);
	void deleteByProductCode(String productCode);
}