package com.example.franchise_manager.presentation.controller;

import org.springframework.web.bind.annotation.*;

import com.example.franchise_manager.application.branch.dto.BranchResponse;
import com.example.franchise_manager.application.branch.dto.BranchResponseWithProduct;
import com.example.franchise_manager.application.branch.dto.CreateBranchRequest;
import com.example.franchise_manager.application.branch.usecases.CreateBranchToFranchiseUseCase;
import com.example.franchise_manager.application.branch.usecases.UpdateBranchNameUseCase;
import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;
import com.example.franchise_manager.presentation.mapper.BranchMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/branches")
public class BranchController {
        private final CreateBranchToFranchiseUseCase createBranchUseCase;
        private final UpdateBranchNameUseCase updateBranchNameUseCase;
        private final BranchMapper branchMapper;

        public BranchController(BranchRepository branchRepository, FranchiseRepository franchiseRepository,
                        BranchMapper branchMapper) {
                this.createBranchUseCase = new CreateBranchToFranchiseUseCase(franchiseRepository, branchRepository);
                this.updateBranchNameUseCase = new UpdateBranchNameUseCase(branchRepository);
                this.branchMapper = branchMapper;
        }

        @PostMapping("/{franchiseId}")
        public BranchResponse save(
                        @PathVariable Long franchiseId,
                        @Valid @RequestBody CreateBranchRequest request) {

                Branch branch = createBranchUseCase.execute(franchiseId, request.getName());

                return branchMapper.branchToBranchResponse(branch);
        }

        @PatchMapping("/{branchId}")
        public BranchResponseWithProduct updateBranch(
                        @PathVariable Long branchId,
                        @Valid @RequestBody CreateBranchRequest request) {

                Branch branch = updateBranchNameUseCase.execute(branchId, request.getName());

                return branchMapper.branchToBranchResponseWithProduct(branch);
        }

}
