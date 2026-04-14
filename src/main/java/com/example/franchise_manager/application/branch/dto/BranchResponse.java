package com.example.franchise_manager.application.branch.dto;

public class BranchResponse {
    private Long id;
    private String name;
    private Long franchiseId;

    public BranchResponse(Long id, String name, Long franchiseId) {
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

}
