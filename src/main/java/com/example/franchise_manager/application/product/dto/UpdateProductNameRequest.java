package com.example.franchise_manager.application.product.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateProductNameRequest {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
