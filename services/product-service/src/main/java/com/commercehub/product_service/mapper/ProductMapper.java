package com.commercehub.product_service.mapper;

import com.commercehub.product_service.dto.request.CreateProductRequest;
import com.commercehub.product_service.dto.response.ProductResponse;
import com.commercehub.product_service.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(CreateProductRequest request);

    ProductResponse toResponse(Product product);

}