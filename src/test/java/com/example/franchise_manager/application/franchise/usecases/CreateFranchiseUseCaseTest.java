package com.example.franchise_manager.application.franchise.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
public class CreateFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private CreateFranchiseUseCase createFranchiseUseCase;

    @Test
    void execute_ShouldCreateAndSaveFranchise() {
        // Arrange
        String name = "Test Franchise";
        Franchise savedFranchise = new Franchise(1L, name);
        when(repository.save(any(Franchise.class))).thenReturn(savedFranchise);

        // Act
        Franchise result = createFranchiseUseCase.execute(name);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(name, result.getName());
        verify(repository).save(any(Franchise.class));
    }
}
