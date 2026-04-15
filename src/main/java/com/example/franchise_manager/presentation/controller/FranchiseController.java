package com.example.franchise_manager.presentation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.franchise_manager.application.franchise.dto.CreateFranchiseRequest;
import com.example.franchise_manager.application.franchise.dto.FranchiseResponse;
import com.example.franchise_manager.application.franchise.usecases.CreateFranchiseUseCase;
import com.example.franchise_manager.application.franchise.usecases.FindAllFranchiseUseCase;
import com.example.franchise_manager.application.franchise.usecases.GetTopProductsByFranchiseUseCase;
import com.example.franchise_manager.application.franchise.usecases.UpdateFranchiseUseCase;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final FindAllFranchiseUseCase findAllFranchiseUseCase;
    private final UpdateFranchiseUseCase updateFranchiseUseCase;
    private final GetTopProductsByFranchiseUseCase getTopProductsByFranchiseUseCase;

    public FranchiseController(FranchiseRepository franchiseRepository,
            BranchRepository branchRepository) {
        this.createFranchiseUseCase = new CreateFranchiseUseCase(franchiseRepository);
        this.findAllFranchiseUseCase = new FindAllFranchiseUseCase(franchiseRepository);
        this.updateFranchiseUseCase = new UpdateFranchiseUseCase(franchiseRepository);
        this.getTopProductsByFranchiseUseCase = new GetTopProductsByFranchiseUseCase(franchiseRepository);
    }

    @PostMapping
    public FranchiseResponse createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        Franchise franchise = createFranchiseUseCase.execute(request.getName());
        return new FranchiseResponse(franchise.getId(), franchise.getName(), null);
    }

    @GetMapping("/top-products/{id}")
    public List<Product> geTopProductByBranch(@PathVariable Long id) {
        List<Product> products = getTopProductsByFranchiseUseCase.execute(id);
        return products;
    }

    @GetMapping()
    public List<Franchise> getFranchises() {
        List<Franchise> franchises = findAllFranchiseUseCase.execute();
        return franchises;
    }

    @PatchMapping("/{id}")
    public FranchiseResponse updateFranchise(@PathVariable Long id,
            @Valid @RequestBody CreateFranchiseRequest request) {
        Franchise franchise = updateFranchiseUseCase.execute(id, request.getName());
        return new FranchiseResponse(franchise.getId(), franchise.getName(), null);
    }

}
