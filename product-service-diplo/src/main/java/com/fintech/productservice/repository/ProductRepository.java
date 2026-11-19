package com.fintech.productservice.repository;

import com.fintech.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCustomerId(Long customerId);

    boolean existsByProductNumber(String productNumber);
}