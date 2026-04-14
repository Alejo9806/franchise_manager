package com.example.franchise_manager.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.franchise_manager.infrastructure.persistence.entity.BranchEntity;

public interface JpaBranchRepository extends JpaRepository<BranchEntity, Long> {
}
