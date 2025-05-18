package com.bongda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.bongda.model.ProductReview;
import com.bongda.respository.ProductReviewRepository;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewRepository reviewRepository;

    public List<ProductReview> getReviewsForProduct(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    public Double getAverageRating(String productId) {
        return Optional.ofNullable(reviewRepository.findAverageRatingByProductId(productId))
                .orElse(0.0);
    }

    public ProductReview addOrUpdateReview(ProductReview review) {
        Optional<ProductReview> existing = reviewRepository
                .findByProductIdAndUserId(review.getProductId(), review.getUserId());

        if (existing.isPresent()) {
            ProductReview r = existing.get();
            r.setRating(review.getRating());
            r.setComment(review.getComment());
            return reviewRepository.save(r);
        }
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id, Long userId) {
        ProductReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!review.getUserId().equals(userId)) {
            throw new AccessDeniedException("Not allowed");
        }
        reviewRepository.delete(review);
    }
}

