package com.example.franchise_manager.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.franchise_manager.application.franchise.dto.CreateFranchiseRequest;
import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FranchiseController.class)
public class FranchiseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FranchiseRepository franchiseRepository;
    
    @MockBean
    private BranchRepository branchRepository;

    @Test
    void createFranchise_ShouldReturnCreatedFranchise() throws Exception {
        // Arrange
        CreateFranchiseRequest request = new CreateFranchiseRequest();
        request.setName("New Franchise");

        Franchise franchise = new Franchise(1L, "New Franchise");

        when(franchiseRepository.save(any(Franchise.class))).thenReturn(franchise);

        // Act & Assert
        mockMvc.perform(post("/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Franchise"));
    }

    @Test
    void geTopProductByBranch_ShouldReturnProducts() throws Exception {
        // Arrange
        Long franchiseId = 1L;
        Franchise franchise = new Franchise(franchiseId, "Franchise 1");
        Branch branch = new Branch(1L, "Branch 1", franchise);
        Product product = new Product(1L, "Product A", 100, branch); // Top stock
        branch.addProduct(product);
        branch.addProduct(new Product(2L, "Product B", 50, branch));
        franchise.addBranch(branch);

        when(franchiseRepository.findById(franchiseId)).thenReturn(franchise);

        // Act & Assert
        mockMvc.perform(get("/franchises/top-products/{id}", franchiseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Product A"))
                .andExpect(jsonPath("$[0].stock").value(100))
                .andExpect(jsonPath("$[0].branch.id").value(1L));
    }

    @Test
    void getFranchises_ShouldReturnAllFranchises() throws Exception {
        // Arrange
        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(1L, "Branch 1", franchise);
        franchise.addBranch(branch);

        when(franchiseRepository.findAll()).thenReturn(Arrays.asList(franchise));

        // Act & Assert
        mockMvc.perform(get("/franchises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Franchise 1"))
                .andExpect(jsonPath("$[0].branches[0].id").value(1L));
    }

    @Test
    void updateFranchise_ShouldReturnUpdatedFranchise() throws Exception {
        // Arrange
        Long franchiseId = 1L;
        CreateFranchiseRequest request = new CreateFranchiseRequest();
        request.setName("Updated Franchise");

        Franchise franchise = new Franchise(franchiseId, "Old Name");
        Franchise updatedFranchise = new Franchise(franchiseId, "Updated Franchise");

        when(franchiseRepository.findById(franchiseId)).thenReturn(franchise);
        when(franchiseRepository.update(franchiseId, "Updated Franchise")).thenReturn(updatedFranchise);

        // Act & Assert
        mockMvc.perform(patch("/franchises/{id}", franchiseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Franchise"));
    }
}
