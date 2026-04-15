package com.example.franchise_manager.application.franchise.usecases;

import java.util.ArrayList;
import java.util.List;

import com.example.franchise_manager.application.branch.dto.BranchResponse;
import com.example.franchise_manager.application.product.dto.ProductResponseWithBranch;
import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

public class GetTopProductsByFranchiseUseCase {
    private final FranchiseRepository repository;

    public GetTopProductsByFranchiseUseCase(FranchiseRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponseWithBranch> execute(Long franchiseId) {

        Franchise franchise = repository.findById(franchiseId);

        List<ProductResponseWithBranch> products = new ArrayList<>();

        for (Branch branch : franchise.getBranches()) {

            Product top = branch.getTopStockProduct();

            if (top != null) {
                products.add(new ProductResponseWithBranch(top.getId(), top.getName(), top.getStock(),
                        new BranchResponse(branch.getId(), branch.getName())));
            }
        }

        return products;
    }
}
