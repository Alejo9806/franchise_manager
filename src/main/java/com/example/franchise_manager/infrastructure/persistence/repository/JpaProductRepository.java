package com.example.franchise_manager.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.franchise_manager.infrastructure.persistence.entity.ProductEntity;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

}
