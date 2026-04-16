package com.example.franchise_manager.presentation.controller;

import org.springframework.web.bind.annotation.*;

import com.example.franchise_manager.application.product.dto.CreateProductRequest;
import com.example.franchise_manager.application.product.dto.ProductResponse;
import com.example.franchise_manager.application.product.dto.UpdateProductNameRequest;
import com.example.franchise_manager.application.product.dto.UpdateProductStockRequest;
import com.example.franchise_manager.application.product.usecases.CreateProductToBranchUseCase;
import com.example.franchise_manager.application.product.usecases.DeleteProductUseCase;
import com.example.franchise_manager.application.product.usecases.UpdateProductNameUseCase;
import com.example.franchise_manager.application.product.usecases.UpdateProductStockUseCase;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.ProductRepository;
import com.example.franchise_manager.presentation.mapper.ProductMapper;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final CreateProductToBranchUseCase saveUseCase;
    private final UpdateProductStockUseCase updateStockUseCase;
    private final UpdateProductNameUseCase updateNameUseCase;
    private final DeleteProductUseCase deleteUseCase;
    private final ProductMapper productMapper;

    public ProductController(ProductRepository productRepository, BranchRepository branchRepository,
            ProductMapper productMapper) {
        this.saveUseCase = new CreateProductToBranchUseCase(productRepository, branchRepository);
        this.updateStockUseCase = new UpdateProductStockUseCase(productRepository);
        this.updateNameUseCase = new UpdateProductNameUseCase(productRepository);
        this.deleteUseCase = new DeleteProductUseCase(productRepository, branchRepository);
        this.productMapper = productMapper;
    }

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @PostMapping("/{branchId}")
    public ProductResponse save(
            @PathVariable Long branchId,
            @Valid @RequestBody CreateProductRequest request) {

        Product product = saveUseCase.execute(
                branchId,
                request.getName(),
                request.getStock());

        return productMapper.productToProductResponse(product);
    }

    @PatchMapping("/{productId}/stock")
    public ProductResponse updateStock(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductStockRequest request) {

        Product product = updateStockUseCase.execute(productId, request.getStock());

        return productMapper.productToProductResponse(product);
    }

    @PatchMapping("/{productId}/name")
    public ProductResponse updateName(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductNameRequest request) {

        Product product = updateNameUseCase.execute(productId, request.getName());

        return productMapper.productToProductResponse(product);
    }

    @DeleteMapping("/{productId}")
    public String delete(
            @PathVariable Long productId) {

        deleteUseCase.execute(productId);

        return "Product deleted successfully";
    }
}
