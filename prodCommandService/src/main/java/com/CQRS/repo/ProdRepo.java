package com.CQRS.repo;

import com.CQRS.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdRepo extends JpaRepository<Product, Long> {
}
