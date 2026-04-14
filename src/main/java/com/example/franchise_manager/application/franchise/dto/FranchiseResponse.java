package com.example.franchise_manager.application.franchise.dto;

import java.util.List;

import com.example.franchise_manager.application.branch.dto.BranchResponse;

public class FranchiseResponse {
    private Long id;
    private String name;
    private List<BranchResponse> branches;

    public FranchiseResponse(Long id, String name, List<BranchResponse> branches) {
        this.id = id;
        this.name = name;
        this.branches = branches;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BranchResponse> getBranches() {
        return branches;
    }
}
