package com.example.franchise_manager.application.branch.usecases;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.repository.BranchRepository;

public class UpdateBranchNameUseCase {
    private final BranchRepository branchRepository;

    public UpdateBranchNameUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch execute(Long branchId, String branchName) {
        Branch branch = branchRepository.findById(branchId);

        if (branch == null) {
            throw new IllegalArgumentException("Branch not found with id: " + branchId);
        }

        branch.setName(branchName);
        return branchRepository.update(branchId, branchName);
    }
}
