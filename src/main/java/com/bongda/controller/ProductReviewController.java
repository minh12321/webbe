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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bongda.model.ProductReview;
import com.bongda.service.ProductReviewService;

@RestController
@CrossOrigin(origins = "https://ab-mocha.vercel.app")
@RequestMapping("/shopbongda/api/products/{productId}/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService reviewService;

    @GetMapping
    public List<ProductReview> getAllReviews(@PathVariable String productId) {
        return reviewService.getReviewsForProduct(productId);
    }

    @GetMapping("/average")
    public Double getAverageRating(@PathVariable String productId) {
        return reviewService.getAverageRating(productId);
    }

    @PostMapping
    public ResponseEntity<ProductReview> createOrUpdateReview(
            @PathVariable String productId,
            @RequestBody ProductReview review,
            @RequestHeader("userId") Long userId) {

        review.setProductId(productId);
        review.setUserId(userId);
        ProductReview saved = reviewService.addOrUpdateReview(review);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("userId") Long userId) {

        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }
}
