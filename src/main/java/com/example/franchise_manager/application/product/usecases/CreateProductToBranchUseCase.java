package com.example.franchise_manager.application.product.usecases;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.ProductRepository;

public class CreateProductToBranchUseCase {
    private final ProductRepository repository;
    private final BranchRepository branchRepository;

    public CreateProductToBranchUseCase(ProductRepository repository, BranchRepository branchRepository) {
        this.repository = repository;
        this.branchRepository = branchRepository;
    }

    public Product execute(Long branchId, String name, int stock) {
        if (branchId == null) {
            throw new IllegalArgumentException("Branch not found");
        }
        Branch branch = branchRepository.findById(branchId);

        if (branch == null) {
            throw new IllegalArgumentException("Branch not found");
        }

        Product product = new Product(null, name, stock, branchId);
        branch.addProduct(product);
        return repository.save(product, branchId);
    }
}
