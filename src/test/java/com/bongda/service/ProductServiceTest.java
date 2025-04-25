package com.bongda.service;


import com.bongda.model.Product;
import com.bongda.respository.ProductRepository;
import com.bongda.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WithMockUser(username = "admin", roles = "ADMIN")
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProduct = new Product();
        sampleProduct.setMaSanPham("20250418120000123");
        sampleProduct.setTenSanPham("Áo CLB Real Madrid");
        sampleProduct.setGia(500000);
        sampleProduct.setSoLuong(10);
        sampleProduct.setMaDanhMuc(1);
        sampleProduct.setMoTa("Áo đấu mùa giải 2024/25");
        sampleProduct.setMauSac("Trắng");
        sampleProduct.setSize("L");
        sampleProduct.setHinhAnh("images/real.jpg");
        sampleProduct.setGiamGia(10);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = List.of(sampleProduct);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Áo CLB Real Madrid", result.get(0).getTenSanPham());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById("20250418120000123")).thenReturn(Optional.of(sampleProduct));

        Optional<Product> result = productService.getProductById("20250418120000123");

        assertTrue(result.isPresent());
        assertEquals("Trắng", result.get().getMauSac());
        verify(productRepository).findById("20250418120000123");
    }

    @Test
    void testSaveProduct() {
        when(productRepository.save(sampleProduct)).thenReturn(sampleProduct);

        Product result = productService.saveProduct(sampleProduct);

        assertEquals("L", result.getSize());
        assertEquals(10, result.getSoLuong());
        verify(productRepository).save(sampleProduct);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById("20250418120000123");

        productService.deleteProduct("20250418120000123");

        verify(productRepository).deleteById("20250418120000123");
    }
}
