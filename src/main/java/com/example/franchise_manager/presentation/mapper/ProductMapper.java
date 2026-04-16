package com.example.franchise_manager.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.franchise_manager.application.product.dto.ProductResponse;
import com.example.franchise_manager.application.product.dto.ProductResponseWithBranch;
import com.example.franchise_manager.domain.model.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductResponse productToProductResponse(Product product);

    ProductResponseWithBranch productToProductResponseWithBranch(Product product);
}
