package com.example.franchise_manager.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.franchise_manager.application.franchise.dto.FranchiseResponse;
import com.example.franchise_manager.domain.model.Franchise;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { BranchMapper.class })
public interface FranchiseMapper {
    FranchiseResponse franchiseToFranchiseResponse(Franchise franchise);
}
