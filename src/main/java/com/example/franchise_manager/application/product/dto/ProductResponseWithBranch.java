package com.example.franchise_manager.application.product.dto;

import com.example.franchise_manager.application.branch.dto.BranchResponse;

public class ProductResponseWithBranch {
    private Long id;
    private String name;
    private int stock;
    private BranchResponse branch;

    public ProductResponseWithBranch(Long id, String name, int stock, BranchResponse branch) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.branch = branch;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public BranchResponse getBranch() {
        return branch;
    }
}
