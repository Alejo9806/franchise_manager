package com.example.franchise_manager.application.branch.usecases;

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
import com.example.franchise_manager.domain.repository.BranchRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateBranchNameUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private UpdateBranchNameUseCase updateBranchNameUseCase;

    @Test
    void execute_ShouldUpdateAndReturnBranch_WhenBranchExists() {
        // Arrange
        Long branchId = 1L;
        String newName = "Updated Branch Name";
        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch existingBranch = new Branch(branchId, "Old Name", franchise);
        Branch updatedBranch = new Branch(branchId, newName, franchise);
        
        when(branchRepository.findById(branchId)).thenReturn(existingBranch);
        when(branchRepository.update(branchId, newName)).thenReturn(updatedBranch);

        // Act
        Branch result = updateBranchNameUseCase.execute(branchId, newName);

        // Assert
        assertNotNull(result);
        assertEquals(newName, result.getName());
        verify(branchRepository).findById(branchId);
        verify(branchRepository).update(branchId, newName);
    }

    @Test
    void execute_ShouldThrowException_WhenBranchDoesNotExist() {
        // Arrange
        Long branchId = 1L;
        String newName = "Updated Branch Name";
        
        when(branchRepository.findById(branchId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> updateBranchNameUseCase.execute(branchId, newName));
            
        assertEquals("Branch not found with id: " + branchId, exception.getMessage());
        verify(branchRepository).findById(branchId);
    }
}
