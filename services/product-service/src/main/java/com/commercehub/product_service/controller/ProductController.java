package com.commercehub.product_service.controller;

import com.commercehub.product_service.dto.request.BulkProductRequest;
import com.commercehub.product_service.dto.request.CreateProductRequest;
import com.commercehub.product_service.dto.response.ProductResponse;
import com.commercehub.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        return productService.createProduct(request);
    }

    @GetMapping("{id}")
    @ResponseBody()
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }


    @GetMapping("/search")
    public Page<ProductResponse> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productService.searchProducts(
                keyword, categoryId, pageable
        );
    }

    @PostMapping("/bulk")
    public List<ProductResponse> getProducts(@Valid @RequestBody
                                             BulkProductRequest request) {

        return productService.getProducts(request);

    }
}