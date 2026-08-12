package com.commercehub.product_service.service.impl;

import com.commercehub.product_service.constant.ProductStatus;
import com.commercehub.product_service.dto.request.BulkProductRequest;
import com.commercehub.product_service.dto.request.CreateProductRequest;
import com.commercehub.product_service.dto.response.ProductResponse;
import com.commercehub.product_service.entity.Product;
import com.commercehub.product_service.exception.DuplicateResourceException;
import com.commercehub.product_service.exception.ResourceNotFoundException;
import com.commercehub.product_service.mapper.ProductMapper;
import com.commercehub.product_service.repository.ProductRepository;
import com.commercehub.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        if (repository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException(
                    "Product SKU already exists: " + request.getSku());
        }

        Product product = mapper.toEntity(request);

        product.setStatus(ProductStatus.DRAFT);

        Product savedProduct = repository.save(product);

        return mapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found : " + id));

        return mapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(String keyword, Long categoryId, Pageable pageable) {
        Page<Product> products = repository.searchProducts(
                keyword, categoryId, pageable);
        return products.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts(BulkProductRequest request) {
        List<Product> products =
                repository.findAllById(
                        request.getProductIds());

        return products.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductBySku(String sku) {
        Product product = repository.findBySku(sku)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with SKU: " + sku));

        return mapper.toResponse(product);
    }
}