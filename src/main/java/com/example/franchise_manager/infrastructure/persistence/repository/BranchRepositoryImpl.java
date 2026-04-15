package com.example.franchise_manager.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.infrastructure.persistence.entity.BranchEntity;
import com.example.franchise_manager.infrastructure.persistence.entity.FranchiseEntity;

@Repository
public class BranchRepositoryImpl implements BranchRepository {

    private final JpaBranchRepository jpaBranchRepository;
    private final JpaFranchiseRepository jpaFranchiseRepository;

    public BranchRepositoryImpl(JpaBranchRepository jpaBranchRepository,
            JpaFranchiseRepository jpaFranchiseRepository) {
        this.jpaBranchRepository = jpaBranchRepository;
        this.jpaFranchiseRepository = jpaFranchiseRepository;
    }

    @Override
    public Branch findById(Long id) {
        return jpaBranchRepository.findById(id)
                .map(this::mapToDomain)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
    }

    @Override
    public Branch save(Branch branch, Long franchiseId) {
        FranchiseEntity franchise = jpaFranchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new IllegalArgumentException("Franchise not found: " + franchiseId));

        BranchEntity entity = new BranchEntity();
        entity.setName(branch.getName());
        entity.setFranchise(franchise);

        BranchEntity saved = jpaBranchRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Branch update(Long id, String name) {
        BranchEntity entity = jpaBranchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        entity.setName(name);
        BranchEntity saved = jpaBranchRepository.save(entity);
        return mapToDomain(saved);
    }

    // @Override
    // public Branch saveProduct(Branch branch) {

    // BranchEntity entity = jpaBranchRepository.findById(branch.getId() != null ?
    // branch.getId() : 0)
    // .orElseThrow(() -> new IllegalArgumentException("Branch not found: " +
    // branch.getId()));

    // if (branch.getProducts() != null) {
    // branch.getProducts().stream()
    // .filter(p -> p.getId() == null)
    // .forEach(product -> {
    // ProductEntity pe = new ProductEntity();
    // pe.setName(product.getName());
    // pe.setStock(product.getStock());
    // pe.setBranch(entity);
    // entity.getProducts().add(pe);
    // });
    // }

    // if (entity.getProducts() != null) {
    // return mapToDomain(entity);
    // }

    // BranchEntity saved = jpaBranchRepository.save(entity);
    // return mapToDomain(saved);
    // }

    private Branch mapToDomain(BranchEntity entity) {
        Branch branch = new Branch(entity.getId(), entity.getName(), entity.getFranchise().getId());

        if (entity.getProducts() != null) {
            entity.getProducts().forEach(
                    pe -> branch.addProduct(new Product(pe.getId(), pe.getName(), pe.getStock(), branch.getId())));
        }

        return branch;
    }
}
