package com.example.franchise_manager.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.ProductRepository;
import com.example.franchise_manager.infrastructure.persistence.entity.BranchEntity;
import com.example.franchise_manager.infrastructure.persistence.entity.ProductEntity;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;
    private final JpaBranchRepository jpaBranchRepository;

    public ProductRepositoryImpl(JpaProductRepository jpaProductRepository, JpaBranchRepository jpaBranchRepository) {
        this.jpaProductRepository = jpaProductRepository;
        this.jpaBranchRepository = jpaBranchRepository;
    }

    @Override
    public Product save(Product product, Long branchId) {
        BranchEntity branch = jpaBranchRepository.findById(branchId).orElseThrow(
                () -> new IllegalArgumentException("Branch not found with id: " + branchId));

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setStock(product.getStock());
        productEntity.setBranch(branch);

        ProductEntity savedProduct = jpaProductRepository.save(productEntity);

        return new Product(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getStock(),
                new Branch(branch.getId(), branch.getName(),
                        new Franchise(branch.getFranchise().getId(), branch.getFranchise().getName())));
    }

    @Override
    public Product findById(Long id) {
        return jpaProductRepository.findById(id).map(this::mapToDomain).orElse(null);

    }

    @Override
    public Product updateName(Long id, String name) {
        ProductEntity productEntity = jpaProductRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found with id: " + id));

        productEntity.setName(name);
        ProductEntity updatedProduct = jpaProductRepository.save(productEntity);

        return mapToDomain(updatedProduct);
    }

    @Override
    public Product updateStock(Long id, int stock) {
        ProductEntity productEntity = jpaProductRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found with id: " + id));

        productEntity.setStock(stock);
        ProductEntity updatedProduct = jpaProductRepository.save(productEntity);

        return mapToDomain(updatedProduct);
    }

    @Override
    public Product remove(Long id) {
        ProductEntity productEntity = jpaProductRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found with id: " + id));

        jpaProductRepository.deleteById(id);

        return mapToDomain(productEntity);
    }

    private Product mapToDomain(ProductEntity entity) {
        Product product = new Product(entity.getId(), entity.getName(), entity.getStock(),
                new Branch(entity.getBranch().getId(), entity.getBranch().getName(),
                        new Franchise(entity.getBranch().getFranchise().getId(),
                                entity.getBranch().getFranchise().getName())));

        return product;
    }

}
