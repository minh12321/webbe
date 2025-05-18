package com.bongda.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bongda.model.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProductId(String productId);

    @Query("SELECT AVG(r.rating) FROM ProductReview r WHERE r.productId = :productId")
    Double findAverageRatingByProductId(@Param("productId") String productId);

    boolean existsByProductIdAndUserId(String productId, Long userId);

    Optional<ProductReview> findByProductIdAndUserId(String productId, Long userId);
}

