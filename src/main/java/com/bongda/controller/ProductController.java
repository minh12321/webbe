package com.bongda.controller;

import com.bongda.model.Product;
import com.bongda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://ab11111.netlify.app")
@RequestMapping("/shopbongda/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productService.getProductById(id).map(product -> {
            product.setTenSanPham(updatedProduct.getTenSanPham());
            product.setGia(updatedProduct.getGia());
            product.setSoLuong(updatedProduct.getSoLuong());
            product.setMaDanhMuc(updatedProduct.getMaDanhMuc());
            product.setMoTa(updatedProduct.getMoTa());
            product.setMauSac(updatedProduct.getMauSac());
            product.setSize(updatedProduct.getSize());
            product.setHinhAnh(updatedProduct.getHinhAnh());
            return ResponseEntity.ok(productService.saveProduct(product));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
