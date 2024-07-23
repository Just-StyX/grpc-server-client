package com.jsl.repository;

import com.jsl.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, String> {
}
