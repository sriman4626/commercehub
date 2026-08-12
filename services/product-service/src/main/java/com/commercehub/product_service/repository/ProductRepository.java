package com.commercehub.product_service.repository;

import com.commercehub.product_service.entity.Product;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    boolean existsBySku(String sku);

    @Override
    @NonNull Optional<Product> findById(@NonNull Long id);


    @Query("""
            SELECT p
            FROM Product p
            WHERE
            (:keyword is NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword, '%')) )
            AND
            (:categoryId is NULL OR p.categoryId =: categoryId)
            """)
    Page<Product> searchProducts(
            @Param("keyword") String Keyword,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    Optional<Product> findBySku(String sku);
}