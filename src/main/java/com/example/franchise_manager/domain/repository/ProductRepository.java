package com.example.franchise_manager.domain.repository;

import com.example.franchise_manager.domain.model.Product;

public interface ProductRepository {

    Product save(Product product, Long branchId);

    Product findById(Long id);

    Product updateName(Long id, String name);

    Product updateStock(Long id, int stock);

    Product remove(Long id);
}
