package com.example.franchise_manager.domain.repository;

import java.util.List;

import com.example.franchise_manager.domain.model.Franchise;

public interface FranchiseRepository {

    Franchise save(Franchise franchise);

    Franchise findById(Long id);

    List<Franchise> findAll();

}
