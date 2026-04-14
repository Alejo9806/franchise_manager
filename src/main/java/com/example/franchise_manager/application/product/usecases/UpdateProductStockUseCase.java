package com.example.franchise_manager.application.product.usecases;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;

public class UpdateProductStockUseCase {
    private final BranchRepository repository;

    public UpdateProductStockUseCase(BranchRepository repository) {
        this.repository = repository;
    }

    public Product execute(Long branchId, Long productId, int stock) {

        Branch branch = repository.findById(branchId);

        Product product = branch.findProductById(productId);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        product.updateStock(stock);

        repository.save(branch);

        return product;
    }
}
