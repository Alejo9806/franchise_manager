package com.example.franchise_manager.application.branch.dto;

import java.util.List;

import com.example.franchise_manager.application.product.dto.ProductResponse;

public class BranchResponseWithProduct {
    private Long id;
    private String name;
    private List<ProductResponse> products;

    public BranchResponseWithProduct(Long id, String name, List<ProductResponse> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

}
