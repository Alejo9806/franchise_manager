package com.example.franchise_manager.application.branch.dto;

import java.util.List;

import com.example.franchise_manager.application.product.dto.ProductResponse;

public class BranchResponse {
    private Long id;
    private String name;
    private Long franchiseId;
    private List<ProductResponse> products;

    public BranchResponse(Long id, String name, Long franchiseId, List<ProductResponse> products) {
        this.id = id;
        this.name = name;
        this.franchiseId = franchiseId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getFranchiseId() {
        return franchiseId;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

}
