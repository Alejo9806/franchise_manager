package com.example.franchise_manager.presentation.controller;

import java.util.List;
import java.util.stream.Collectors;

// import java.util.List;
// import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.example.franchise_manager.application.branch.dto.BranchResponse;
import com.example.franchise_manager.application.branch.dto.CreateBranchRequest;
import com.example.franchise_manager.application.branch.usecases.CreateBranchToFranchiseUseCase;
import com.example.franchise_manager.application.franchise.dto.CreateFranchiseRequest;
import com.example.franchise_manager.application.franchise.dto.FranchiseResponse;
import com.example.franchise_manager.application.franchise.usecases.CreateFranchiseUseCase;
import com.example.franchise_manager.application.franchise.usecases.FindAllFranchiseUseCase;
import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final CreateBranchToFranchiseUseCase createBranchUseCase;
    private final FindAllFranchiseUseCase findAllFranchiseUseCase;

    public FranchiseController(FranchiseRepository franchiseRepository,
            BranchRepository branchRepository) {
        this.createFranchiseUseCase = new CreateFranchiseUseCase(franchiseRepository);
        this.createBranchUseCase = new CreateBranchToFranchiseUseCase(franchiseRepository, branchRepository);
        this.findAllFranchiseUseCase = new FindAllFranchiseUseCase(franchiseRepository);
    }

    @PostMapping
    public FranchiseResponse createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        Franchise franchise = createFranchiseUseCase.execute(request.getName());
        return new FranchiseResponse(franchise.getId(), franchise.getName(), null);
    }

    @PostMapping("/{franchiseId}/branches")
    public BranchResponse addBranch(
            @PathVariable Long franchiseId,
            @Valid @RequestBody CreateBranchRequest request) {

        Branch branch = createBranchUseCase.execute(franchiseId, request.getName());

        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                franchiseId);
    }

    @GetMapping()
    public List<Franchise> getFranchises() {
        List<Franchise> franchises = findAllFranchiseUseCase.execute();
        return franchises;
    }

}
