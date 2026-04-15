package com.example.franchise_manager.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private Long id;
    private String name;
    private List<Product> products;
    private Franchise franchise;

    public Branch(Long id, String name, Franchise franchise) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Branch name cannot be empty");
        }
        this.id = id;
        this.name = name;
        this.products = new ArrayList<>();
        this.franchise = franchise;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Long productId) {
        this.products.removeIf(p -> p.getId().equals(productId));
    }

    public Product findProductById(Long productId) {
        return this.products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public Product getTopStockProduct() {
        return this.products.stream()
                .max((p1, p2) -> Integer.compare(p1.getStock(), p2.getStock()))
                .orElse(null);
    }
}
