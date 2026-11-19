package com.fintech.productservice.mapper;

import com.fintech.productservice.dto.ProductRequestDto;
import com.fintech.productservice.dto.ProductResponseDto;
import com.fintech.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto) {
            Product product = new Product();
            product.setCustomerId(dto.customerId());
            product.setType(dto.type());
            product.setProductNumber(dto.productNumber());
            product.setAmount(dto.amount());
            product.setActive(true);
            return product;
    }

    public ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getCustomerId(),
                product.getType(),
                product.getProductNumber(),
                product.getAmount(),
                product.getActive()
        );
    }
}