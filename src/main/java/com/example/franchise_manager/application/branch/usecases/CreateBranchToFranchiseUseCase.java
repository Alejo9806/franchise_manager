package com.example.franchise_manager.application.branch.usecases;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

public class CreateBranchToFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    public CreateBranchToFranchiseUseCase(FranchiseRepository franchiseRepository,
            BranchRepository branchRepository) {
        this.franchiseRepository = franchiseRepository;
        this.branchRepository = branchRepository;
    }

    public Branch execute(Long franchiseId, String branchName) {
        if (franchiseRepository.findById(franchiseId) == null) {
            throw new IllegalArgumentException("Franchise not found with id: " + franchiseId);
        }

        Branch branch = new Branch(null, branchName);
        return branchRepository.saveNew(branch, franchiseId);
    }
}
