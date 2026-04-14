package com.example.franchise_manager.application.product.usecases;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.repository.BranchRepository;

public class DeleteProductUseCase {
    private final BranchRepository repository;

    public DeleteProductUseCase(BranchRepository repository) {
        this.repository = repository;
    }

    public void execute(Long branchId, Long productId) {
        Branch branch = repository.findById(branchId);

        if (branch == null) {
            throw new IllegalArgumentException("Branch not found");
        }

        branch.removeProduct(productId);
        repository.save(branch);
    }
}
