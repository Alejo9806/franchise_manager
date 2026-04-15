package com.example.franchise_manager.application.franchise.usecases;

import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

public class UpdateFranchiseUseCase {

    private final FranchiseRepository repository;

    public UpdateFranchiseUseCase(FranchiseRepository repository) {
        this.repository = repository;
    }

    public Franchise execute(Long id, String name) {
        Franchise franchise = repository.findById(id);

        if (franchise == null) {
            throw new IllegalArgumentException("Franchise not found with id: " + id);
        }

        franchise.setName(name);
        return repository.update(id, name);
    }
}
