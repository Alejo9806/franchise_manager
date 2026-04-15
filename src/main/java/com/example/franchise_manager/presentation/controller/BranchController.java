package com.example.franchise_manager.presentation.controller;

import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.example.franchise_manager.application.branch.dto.BranchResponse;
import com.example.franchise_manager.application.branch.dto.CreateBranchRequest;
import com.example.franchise_manager.application.branch.usecases.CreateBranchToFranchiseUseCase;
import com.example.franchise_manager.application.branch.usecases.UpdateBranchNameUseCase;
import com.example.franchise_manager.application.product.dto.ProductResponse;
import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/branches")
public class BranchController {
    private final CreateBranchToFranchiseUseCase createBranchUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;

    public BranchController(BranchRepository branchRepository, FranchiseRepository franchiseRepository) {
        this.createBranchUseCase = new CreateBranchToFranchiseUseCase(franchiseRepository, branchRepository);
        this.updateBranchNameUseCase = new UpdateBranchNameUseCase(branchRepository);
    }

    @PostMapping("/{franchiseId}")
    public BranchResponse save(
            @PathVariable Long franchiseId,
            @Valid @RequestBody CreateBranchRequest request) {

        Branch branch = createBranchUseCase.execute(franchiseId, request.getName());

        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                franchiseId,
                branch.getProducts().stream()
                        .map(product -> new ProductResponse(product.getId(), product.getName(), product.getStock(),
                                franchiseId))
                        .collect(Collectors.toList()));
    }

    @PatchMapping("/{branchId}")
    public BranchResponse updateBranch(
            @PathVariable Long branchId,
            @Valid @RequestBody CreateBranchRequest request) {

        Branch branch = updateBranchNameUseCase.execute(branchId, request.getName());

        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getFranchiseId(),
                branch.getProducts().stream()
                        .map(product -> new ProductResponse(product.getId(), product.getName(), product.getStock(),
                                branch.getFranchiseId()))
                        .collect(Collectors.toList()));
    }

}
