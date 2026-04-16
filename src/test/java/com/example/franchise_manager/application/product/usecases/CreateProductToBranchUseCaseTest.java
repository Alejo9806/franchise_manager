package com.example.franchise_manager.application.product.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class CreateProductToBranchUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private CreateProductToBranchUseCase createProductToBranchUseCase;

    @Test
    void execute_ShouldCreateAndSaveProduct_WhenBranchExists() {
        // Arrange
        Long branchId = 1L;
        String name = "Test Product";
        int stock = 50;
        
        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(branchId, "Branch 1", franchise);
        
        when(branchRepository.findById(branchId)).thenReturn(branch);
        
        Product expectedSavedProduct = new Product(1L, name, stock, branch);
        when(productRepository.save(any(Product.class), eq(branchId))).thenReturn(expectedSavedProduct);

        // Act
        Product result = createProductToBranchUseCase.execute(branchId, name, stock);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(name, result.getName());
        assertEquals(stock, result.getStock());
        assertEquals(1, branch.getProducts().size()); // Ensure product was added to branch
        
        verify(branchRepository).findById(branchId);
        verify(productRepository).save(any(Product.class), eq(branchId));
    }

    @Test
    void execute_ShouldThrowException_WhenBranchIdIsNull() {
        // Arrange
        Long branchId = null;
        String name = "Test Product";
        int stock = 50;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> createProductToBranchUseCase.execute(branchId, name, stock));
            
        assertEquals("Branch not found", exception.getMessage());
    }

    @Test
    void execute_ShouldThrowException_WhenBranchDoesNotExist() {
        // Arrange
        Long branchId = 1L;
        String name = "Test Product";
        int stock = 50;
        
        when(branchRepository.findById(branchId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> createProductToBranchUseCase.execute(branchId, name, stock));
            
        assertEquals("Branch not found", exception.getMessage());
        verify(branchRepository).findById(branchId);
    }
}
