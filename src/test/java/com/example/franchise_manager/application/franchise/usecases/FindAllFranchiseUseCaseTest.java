package com.example.franchise_manager.application.franchise.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.repository.FranchiseRepository;

@ExtendWith(MockitoExtension.class)
public class FindAllFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private FindAllFranchiseUseCase findAllFranchiseUseCase;

    @Test
    void execute_ShouldReturnListOfFranchises() {
        // Arrange
        List<Franchise> franchises = Arrays.asList(
            new Franchise(1L, "Franchise 1"),
            new Franchise(2L, "Franchise 2")
        );
        when(repository.findAll()).thenReturn(franchises);

        // Act
        List<Franchise> result = findAllFranchiseUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Franchise 1", result.get(0).getName());
        verify(repository).findAll();
    }
}
