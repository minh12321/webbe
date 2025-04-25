package com.bongda.controller;


import com.bongda.model.Product;
import com.bongda.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin", roles = "ADMIN")
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setMaSanPham("P001");
        product.setTenSanPham("Áo Đấu");
        product.setGia(200000);
        product.setSoLuong(10);
        product.setMaDanhMuc(1);
        product.setMoTa("Áo đá banh chất lượng cao");
        product.setMauSac("Đỏ");
        product.setSize("L");
        product.setHinhAnh("img/ao.jpg");
        
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/shopbongda/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenSanPham").value("Áo Đấu"));
    }

    @Test
    void testGetProductByIdFound() throws Exception {
        when(productService.getProductById("P001")).thenReturn(Optional.of(product));

        mockMvc.perform(get("/shopbongda/api/products/P001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maSanPham").value("P001"));
    }

    @Test
    void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById("P999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/shopbongda/api/products/P999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/shopbongda/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenSanPham").value("Áo Đấu"));
    }

    @Test
    void testUpdateProductFound() throws Exception {
        when(productService.getProductById("P001")).thenReturn(Optional.of(product));
        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        product.setTenSanPham("Áo Mới");

        mockMvc.perform(put("/shopbongda/api/products/P001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenSanPham").value("Áo Mới"));
    }

    @Test
    void testUpdateProductNotFound() throws Exception {
        when(productService.getProductById("P999")).thenReturn(Optional.empty());

        mockMvc.perform(put("/shopbongda/api/products/P999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/shopbongda/api/products/P001"))
                .andExpect(status().isNoContent());

        Mockito.verify(productService).deleteProduct("P001");
    }

    // Helper method to convert object to JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
