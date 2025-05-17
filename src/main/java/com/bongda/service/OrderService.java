package com.bongda.service;

import com.bongda.model.*;
import com.bongda.respository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setMaHoaDon(orderDetails.getMaHoaDon());
        order.setTenNguoiDat(orderDetails.getTenNguoiDat());
        order.setSoDienThoai(orderDetails.getSoDienThoai());
        order.setDiaChiGiaoHang(orderDetails.getDiaChiGiaoHang());
        order.setTrangThaiGiaoHang(orderDetails.getTrangThaiGiaoHang());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
