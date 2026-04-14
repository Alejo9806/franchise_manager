package com.example.franchise_manager.application.franchise.dto;

public class FranchiseResponse {
    private Long id;
    private String name;

    public FranchiseResponse(Long id, String name) {
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
