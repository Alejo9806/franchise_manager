package com.example.franchise_manager.presentation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.example.franchise_manager.application.franchise.dto.CreateFranchiseRequest;
import com.example.franchise_manager.application.franchise.dto.FranchiseResponse;
import com.example.franchise_manager.application.franchise.usecases.CreateFranchiseUseCase;
import com.example.franchise_manager.application.franchise.usecases.FindAllFranchiseUseCase;
import com.example.franchise_manager.application.franchise.usecases.GetTopProductsByFranchiseUseCase;
import com.example.franchise_manager.application.franchise.usecases.UpdateFranchiseUseCase;
import com.example.franchise_manager.application.product.dto.ProductResponseWithBranch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;
import com.example.franchise_manager.presentation.mapper.FranchiseMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final FindAllFranchiseUseCase findAllFranchiseUseCase;
    private final UpdateFranchiseUseCase updateFranchiseUseCase;
    private final GetTopProductsByFranchiseUseCase getTopProductsByFranchiseUseCase;
    private final FranchiseMapper franchiseMapper;

    public FranchiseController(FranchiseRepository franchiseRepository,
            BranchRepository branchRepository, FranchiseMapper franchiseMapper) {
        this.createFranchiseUseCase = new CreateFranchiseUseCase(franchiseRepository);
        this.findAllFranchiseUseCase = new FindAllFranchiseUseCase(franchiseRepository);
        this.updateFranchiseUseCase = new UpdateFranchiseUseCase(franchiseRepository);
        this.getTopProductsByFranchiseUseCase = new GetTopProductsByFranchiseUseCase(franchiseRepository);
        this.franchiseMapper = franchiseMapper;
    }

    @PostMapping
    public FranchiseResponse createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        Franchise franchise = createFranchiseUseCase.execute(request.getName());
        return franchiseMapper.franchiseToFranchiseResponse(franchise);
    }

    @GetMapping("/top-products/{id}")
    public List<ProductResponseWithBranch> geTopProductByBranch(@PathVariable Long id) {
        List<ProductResponseWithBranch> products = getTopProductsByFranchiseUseCase.execute(id);
        return products;
    }

    @GetMapping()
    public List<FranchiseResponse> getFranchises() {
        List<Franchise> franchises = findAllFranchiseUseCase.execute();

        return franchises.stream()
                .map(franchiseMapper::franchiseToFranchiseResponse)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    public FranchiseResponse updateFranchise(@PathVariable Long id,
            @Valid @RequestBody CreateFranchiseRequest request) {
        Franchise franchise = updateFranchiseUseCase.execute(id, request.getName());
        return franchiseMapper.franchiseToFranchiseResponse(franchise);
    }

}
