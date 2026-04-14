package com.example.franchise_manager.application.branch.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateBranchRequest {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
