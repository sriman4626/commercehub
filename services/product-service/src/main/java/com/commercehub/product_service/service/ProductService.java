package com.commercehub.product_service.service;

import com.commercehub.product_service.dto.request.BulkProductRequest;
import com.commercehub.product_service.dto.request.CreateProductRequest;
import com.commercehub.product_service.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProduct(Long id);

    Page<ProductResponse> searchProducts(String keyword, Long categoryId, Pageable pageable);

    List<ProductResponse> getProducts(BulkProductRequest request);
}