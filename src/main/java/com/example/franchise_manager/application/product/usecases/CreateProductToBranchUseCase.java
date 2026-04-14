package com.example.franchise_manager.application.product.usecases;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;

public class CreateProductToBranchUseCase {
    private final BranchRepository repository;

    public CreateProductToBranchUseCase(BranchRepository repository) {
        this.repository = repository;
    }

    public Product execute(Long branchId, String name, int stock) {
        Branch branch = repository.findById(branchId);

        if (branch == null) {
            throw new IllegalArgumentException("Branch not found");
        }

        Product product = new Product(null, name, stock);
        branch.addProduct(product);
        repository.save(branch);
        return product;
    }
}
