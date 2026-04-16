package com.example.franchise_manager.application.franchise.usecases;

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

import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private UpdateFranchiseUseCase updateFranchiseUseCase;

    @Test
    void execute_ShouldUpdateAndReturnFranchise_WhenFranchiseExists() {
        // Arrange
        Long id = 1L;
        String newName = "Updated Franchise";
        Franchise existingFranchise = new Franchise(id, "Old Name");
        Franchise updatedFranchise = new Franchise(id, newName);
        
        when(repository.findById(id)).thenReturn(existingFranchise);
        when(repository.update(id, newName)).thenReturn(updatedFranchise);

        // Act
        Franchise result = updateFranchiseUseCase.execute(id, newName);

        // Assert
        assertNotNull(result);
        assertEquals(newName, result.getName());
        verify(repository).findById(id);
        verify(repository).update(id, newName);
    }

    @Test
    void execute_ShouldThrowException_WhenFranchiseDoesNotExist() {
        // Arrange
        Long id = 1L;
        String newName = "Updated Franchise";
        when(repository.findById(id)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> updateFranchiseUseCase.execute(id, newName));
            
        assertEquals("Franchise not found with id: " + id, exception.getMessage());
        verify(repository).findById(id);
    }
}
