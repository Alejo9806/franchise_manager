package com.example.franchise_manager.domain.repository;

import com.example.franchise_manager.domain.model.Branch;

public interface BranchRepository {

    Branch findById(Long id);

    Branch save(Branch branch, Long franchiseId);

    Branch update(Long id, String name);

    // Branch saveProduct(Branch branch);
}
