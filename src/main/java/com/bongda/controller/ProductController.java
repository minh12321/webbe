package com.bongda.controller;

import com.bongda.model.Product;
import com.bongda.respository.ProductRepository;
import com.bongda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://ab-mocha.vercel.app")
@RequestMapping("/shopbongda/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/admin")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/admin/{id}")
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

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/admin/{id}/giam-gia")
    public ResponseEntity<String> updateDiscount(
            @PathVariable String id,
            @RequestParam("giamGia") int giamGia) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setGiamGia(giamGia);
            productRepository.save(product);
            return ResponseEntity.ok("Cập nhật giảm giá thành công");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/admin/{id}/mat")
    public ResponseEntity<Integer> capNhatSoLuongMat(
            @PathVariable String id,
            @RequestParam("soLuongMat") int soLuongMat) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            int soLuongHienTai = product.getSoLuong();
            int soLuongConLai = Math.max(0, soLuongHienTai - soLuongMat); // Không cho nhỏ hơn 0

            product.setSoLuong(soLuongConLai);
            productRepository.save(product);

            return ResponseEntity.ok(soLuongConLai); // Trả về số lượng còn lại
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
