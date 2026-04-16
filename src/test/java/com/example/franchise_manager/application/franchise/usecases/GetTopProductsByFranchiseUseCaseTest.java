package com.example.franchise_manager.application.franchise.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.franchise_manager.application.product.dto.ProductResponseWithBranch;
import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

@ExtendWith(MockitoExtension.class)
public class GetTopProductsByFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private GetTopProductsByFranchiseUseCase getTopProductsByFranchiseUseCase;

    @Test
    void execute_ShouldReturnTopStockProducts() {
        // Arrange
        Long franchiseId = 1L;
        Franchise franchise = new Franchise(franchiseId, "Franchise 1");
        
        Branch branch1 = new Branch(1L, "Branch 1", franchise);
        branch1.addProduct(new Product(1L, "Prod A", 10, branch1));
        branch1.addProduct(new Product(2L, "Prod B", 30, branch1)); // Top stock
        
        Branch branch2 = new Branch(2L, "Branch 2", franchise);
        branch2.addProduct(new Product(3L, "Prod C", 5, branch2));
        branch2.addProduct(new Product(4L, "Prod D", 20, branch2)); // Top stock
        
        franchise.addBranch(branch1);
        franchise.addBranch(branch2);
        
        when(repository.findById(franchiseId)).thenReturn(franchise);

        // Act
        List<ProductResponseWithBranch> result = getTopProductsByFranchiseUseCase.execute(franchiseId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Prod B", result.get(0).getName());
        assertEquals(30, result.get(0).getStock());
        assertEquals("Prod D", result.get(1).getName());
        assertEquals(20, result.get(1).getStock());
        
        verify(repository).findById(franchiseId);
    }
}
