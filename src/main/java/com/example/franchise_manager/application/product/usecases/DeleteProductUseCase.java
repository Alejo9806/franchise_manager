package com.example.franchise_manager.application.product.usecases;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.ProductRepository;

public class DeleteProductUseCase {
    private final ProductRepository repository;

    private final BranchRepository branchRepository;

    public DeleteProductUseCase(ProductRepository productRepository, BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
        this.repository = productRepository;
    }

    public void execute(Long productId) {
        Product product = repository.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Branch branch = branchRepository.findById(product.getBranchId());

        if (branch == null) {
            throw new IllegalArgumentException("Branch not found");
        }

        branch.removeProduct(productId);
        repository.remove(productId);
    }
}
