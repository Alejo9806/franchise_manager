package com.example.franchise_manager.domain.repository;

import com.example.franchise_manager.domain.model.Branch;

public interface BranchRepository {

    Branch findById(Long id);

    Branch saveNew(Branch branch, Long franchiseId);

    Branch save(Branch branch);
}
