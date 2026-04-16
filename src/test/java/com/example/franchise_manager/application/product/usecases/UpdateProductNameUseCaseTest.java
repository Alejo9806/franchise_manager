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
public class UpdateProductNameUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProductNameUseCase updateProductNameUseCase;

    @Test
    void execute_ShouldUpdateAndReturnProduct_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        String newName = "Updated Product Name";
        
        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(1L, "Branch 1", franchise);
        Product existingProduct = new Product(productId, "Old Name", 10, branch);
        Product updatedProduct = new Product(productId, newName, 10, branch);
        
        when(repository.findById(productId)).thenReturn(existingProduct);
        when(repository.updateName(productId, newName)).thenReturn(updatedProduct);

        // Act
        Product result = updateProductNameUseCase.execute(productId, newName);

        // Assert
        assertNotNull(result);
        assertEquals(newName, result.getName());
        verify(repository).findById(productId);
        verify(repository).updateName(productId, newName);
    }

    @Test
    void execute_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        Long productId = 1L;
        String newName = "Updated Product Name";
        when(repository.findById(productId)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> updateProductNameUseCase.execute(productId, newName));
            
        assertEquals("Product not found", exception.getMessage());
        verify(repository).findById(productId);
    }
}
