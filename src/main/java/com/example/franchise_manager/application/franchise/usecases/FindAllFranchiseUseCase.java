package com.example.franchise_manager.application.franchise.usecases;

import java.util.List;

import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

public class FindAllFranchiseUseCase {
    private final FranchiseRepository repository;

    public FindAllFranchiseUseCase(FranchiseRepository repository) {
        this.repository = repository;
    }

    public List<Franchise> execute() {
        return repository.findAll();
    }
}
