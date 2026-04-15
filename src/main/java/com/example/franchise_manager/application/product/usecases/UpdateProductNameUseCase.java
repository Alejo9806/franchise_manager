package com.example.franchise_manager.application.product.usecases;

import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.ProductRepository;

public class UpdateProductNameUseCase {
    private final ProductRepository repository;

    public UpdateProductNameUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public Product execute(Long productId, String name) {
        Product product = repository.findById(productId);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        product.setName(name);
        return repository.updateName(productId, name);
    }

}
