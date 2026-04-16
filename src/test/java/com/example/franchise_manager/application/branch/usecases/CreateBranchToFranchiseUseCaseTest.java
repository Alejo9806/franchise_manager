package com.example.franchise_manager.application.branch.usecases;

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
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

@ExtendWith(MockitoExtension.class)
public class CreateBranchToFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private CreateBranchToFranchiseUseCase createBranchToFranchiseUseCase;

    @Test
    void execute_ShouldCreateAndSaveBranch_WhenFranchiseExists() {
        // Arrange
        Long franchiseId = 1L;
        String branchName = "New Branch";
        Franchise franchise = new Franchise(franchiseId, "Franchise 1");
        
        when(franchiseRepository.findById(franchiseId)).thenReturn(franchise);
        
        Branch expectedSavedBranch = new Branch(1L, branchName, franchise);
        when(branchRepository.save(any(Branch.class), eq(franchiseId))).thenReturn(expectedSavedBranch);

        // Act
        Branch result = createBranchToFranchiseUseCase.execute(franchiseId, branchName);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(branchName, result.getName());
        assertEquals(1, franchise.getBranches().size()); // Ensure branch was added to franchise
        
        verify(franchiseRepository).findById(franchiseId);
        verify(branchRepository).save(any(Branch.class), eq(franchiseId));
    }

    @Test
    void execute_ShouldThrowException_WhenFranchiseDoesNotExist() {
        // Arrange
        Long franchiseId = 1L;
        String branchName = "New Branch";
        
        when(franchiseRepository.findById(franchiseId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> createBranchToFranchiseUseCase.execute(franchiseId, branchName));
            
        assertEquals("Franchise not found with id: " + franchiseId, exception.getMessage());
        verify(franchiseRepository).findById(franchiseId);
    }
}
