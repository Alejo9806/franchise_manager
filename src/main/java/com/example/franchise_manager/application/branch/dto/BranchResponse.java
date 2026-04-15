package com.example.franchise_manager.application.branch.dto;

public class BranchResponse {
    private Long id;
    private String name;

    public BranchResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
