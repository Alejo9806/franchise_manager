package com.example.franchise_manager.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Franchise {

    private Long id;
    private String name;
    private List<Branch> branches;

    public Franchise(Long id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Franchise name cannot be empty");
        }
        this.id = id;
        this.name = name;
        this.branches = new ArrayList<>();
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

    public List<Branch> getBranches() {
        return branches;
    }

    public void addBranch(Branch branch) {
        this.branches.add(branch);
    }

    public void removeBranch(Branch branch) {
        this.branches.remove(branch);
    }
}
