package com.example.franchise_manager.application.franchise.usecases;

import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

public class CreateFranchiseUseCase {

    private final FranchiseRepository repository;

    public CreateFranchiseUseCase(FranchiseRepository repository) {
        this.repository = repository;
    }

    public Franchise execute(String name) {
        Franchise franchise = new Franchise(null, name);
        return repository.save(franchise);
    }
}
