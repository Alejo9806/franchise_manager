package com.example.franchise_manager.infrastructure.persistence.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.FranchiseRepository;
import com.example.franchise_manager.infrastructure.persistence.entity.BranchEntity;
import com.example.franchise_manager.infrastructure.persistence.entity.FranchiseEntity;

@Repository
public class FranchiseRepositoryImpl implements FranchiseRepository {
    private final JpaFranchiseRepository jpaRepository;

    public FranchiseRepositoryImpl(JpaFranchiseRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Franchise save(Franchise franchise) {
        FranchiseEntity entity = mapToEntity(franchise);

        FranchiseEntity saved = jpaRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Franchise update(Long id, String name) {

        FranchiseEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Franchise not found"));

        entity.setName(name);
        FranchiseEntity saved = jpaRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Franchise findById(Long id) {

        return jpaRepository.findById(id)
                .map(this::mapToDomain)
                .orElse(null);
    }

    @Override
    public List<Franchise> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private FranchiseEntity mapToEntity(Franchise franchise) {
        FranchiseEntity entity = new FranchiseEntity();
        entity.setName(franchise.getName());

        if (franchise.getBranches() != null) {
            entity.getBranches().clear();
            entity.getBranches().addAll(
                    franchise.getBranches().stream()
                            .map(branch -> {
                                BranchEntity branchEntity = new BranchEntity();
                                branchEntity.setName(branch.getName());
                                branchEntity.setFranchise(entity);
                                return branchEntity;
                            })
                            .collect(Collectors.toList()));
        }

        return entity;
    }

    private Franchise mapToDomain(FranchiseEntity entity) {
        Franchise franchise = new Franchise(entity.getId(), entity.getName());

        if (entity.getBranches() != null) {
            entity.getBranches().forEach(branchEntity -> {
                franchise.addBranch(new Branch(branchEntity.getId(), branchEntity.getName(), franchise));
                if (branchEntity.getProducts() != null) {
                    branchEntity.getProducts().forEach(productEntity -> {
                        franchise.getBranches().get(franchise.getBranches().size() - 1)
                                .addProduct(new Product(productEntity.getId(), productEntity.getName(),
                                        productEntity.getStock(),
                                        new Branch(branchEntity.getId(), branchEntity.getName(), franchise)));
                    });
                }
            });
        }

        return franchise;
    }
}
