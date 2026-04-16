package com.example.franchise_manager.application.product.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.example.franchise_manager.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateProductStockUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProductStockUseCase updateProductStockUseCase;

    @Test
    void execute_ShouldUpdateAndReturnProduct_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        int newStock = 100;
        
        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(1L, "Branch 1", franchise);
        Product existingProduct = new Product(productId, "Product 1", 50, branch);
        Product updatedProduct = new Product(productId, "Product 1", newStock, branch);
        
        when(repository.findById(productId)).thenReturn(existingProduct);
        when(repository.updateStock(productId, newStock)).thenReturn(updatedProduct);

        // Act
        Product result = updateProductStockUseCase.execute(productId, newStock);

        // Assert
        assertNotNull(result);
        assertEquals(newStock, result.getStock());
        verify(repository).findById(productId);
        verify(repository).updateStock(productId, newStock);
    }

    @Test
    void execute_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        Long productId = 1L;
        int newStock = 100;
        when(repository.findById(productId)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> updateProductStockUseCase.execute(productId, newStock));
            
        assertEquals("Product not found", exception.getMessage());
        verify(repository).findById(productId);
    }
}
