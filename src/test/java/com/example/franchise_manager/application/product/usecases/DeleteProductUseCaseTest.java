package com.example.franchise_manager.application.product.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
public class DeleteProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;

    @Test
    void execute_ShouldDeleteProductAndRemoveFromBranch() {
        // Arrange
        Long productId = 1L;
        Long branchId = 2L;
        
        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(branchId, "Branch 1", franchise);
        Product product = new Product(productId, "Product 1", 10, branch);
        branch.addProduct(product);
        
        when(productRepository.findById(productId)).thenReturn(product);
        when(branchRepository.findById(branchId)).thenReturn(branch);

        // Act
        deleteProductUseCase.execute(productId);

        // Assert
        assertEquals(0, branch.getProducts().size()); // Ensure it was removed
        verify(productRepository).findById(productId);
        verify(branchRepository).findById(branchId);
        verify(productRepository).remove(productId);
    }

    @Test
    void execute_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> deleteProductUseCase.execute(productId));
            
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository).findById(productId);
    }

    @Test
    void execute_ShouldThrowException_WhenBranchDoesNotExist() {
        // Arrange
        Long productId = 1L;
        Long branchId = 2L;
        
        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(branchId, "Branch 1", franchise);
        Product product = new Product(productId, "Product 1", 10, branch);
        
        when(productRepository.findById(productId)).thenReturn(product);
        when(branchRepository.findById(branchId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> deleteProductUseCase.execute(productId));
            
        assertEquals("Branch not found", exception.getMessage());
        verify(productRepository).findById(productId);
        verify(branchRepository).findById(branchId);
    }
}
