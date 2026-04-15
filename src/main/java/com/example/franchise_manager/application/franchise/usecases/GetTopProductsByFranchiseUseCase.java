package com.example.franchise_manager.application.franchise.usecases;

import java.util.ArrayList;
import java.util.List;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

public class GetTopProductsByFranchiseUseCase {
    private final FranchiseRepository repository;

    public GetTopProductsByFranchiseUseCase(FranchiseRepository repository) {
        this.repository = repository;
    }

    public List<Product> execute(Long franchiseId) {

        Franchise franchise = repository.findById(franchiseId);

        List<Product> products = new ArrayList<>();

        for (Branch branch : franchise.getBranches()) {

            Product top = branch.getTopStockProduct();

            if (top != null) {
                products.add(top);
            }
        }

        return products;
    }
}
