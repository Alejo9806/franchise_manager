package com.example.franchise_manager.application.branch.usecases;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
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
        Franchise franchise = franchiseRepository.findById(franchiseId);
        if (franchise == null) {
            throw new IllegalArgumentException("Franchise not found with id: " + franchiseId);
        }

        Branch branch = new Branch(null, branchName, franchise);
        franchise.addBranch(branch);
        return branchRepository.save(branch, franchiseId);
    }
}
