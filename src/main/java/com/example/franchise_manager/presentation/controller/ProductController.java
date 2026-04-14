package com.example.franchise_manager.presentation.controller;

import org.springframework.web.bind.annotation.*;

import com.example.franchise_manager.application.product.dto.CreateProductRequest;
import com.example.franchise_manager.application.product.dto.ProductResponse;
import com.example.franchise_manager.application.product.usecases.CreateProductToBranchUseCase;
import com.example.franchise_manager.application.product.usecases.DeleteProductUseCase;
import com.example.franchise_manager.application.product.usecases.UpdateProductStockUseCase;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final CreateProductToBranchUseCase addUseCase;
    private final UpdateProductStockUseCase updateUseCase;
    private final DeleteProductUseCase deleteUseCase;

    public ProductController(BranchRepository repository) {
        this.addUseCase = new CreateProductToBranchUseCase(repository);
        this.updateUseCase = new UpdateProductStockUseCase(repository);
        this.deleteUseCase = new DeleteProductUseCase(repository);
    }

    @PostMapping("/branches/{branchId}/products")
    public ProductResponse add(
            @PathVariable Long branchId,
            @Valid @RequestBody CreateProductRequest request) {

        Product product = addUseCase.execute(
                branchId,
                request.getName(),
                request.getStock());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getStock(),
                branchId);
    }

    @PatchMapping("/branches/{branchId}/products/{productId}/stock")
    public ProductResponse updateStock(
            @PathVariable Long branchId,
            @PathVariable Long productId,
            @RequestParam int stock) {

        Product product = updateUseCase.execute(branchId, productId, stock);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getStock(),
                branchId);
    }

    @DeleteMapping("/branches/{branchId}/products/{productId}")
    public void delete(
            @PathVariable Long branchId,
            @PathVariable Long productId) {
        deleteUseCase.execute(branchId, productId);
    }
}
