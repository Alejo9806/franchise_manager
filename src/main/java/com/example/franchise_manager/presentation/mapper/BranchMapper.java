package com.example.franchise_manager.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.franchise_manager.application.branch.dto.BranchResponse;
import com.example.franchise_manager.application.branch.dto.BranchResponseWithProduct;
import com.example.franchise_manager.domain.model.Branch;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { ProductMapper.class })
public interface BranchMapper {
    BranchResponse branchToBranchResponse(Branch branch);

    BranchResponseWithProduct branchToBranchResponseWithProduct(Branch branch);
}
