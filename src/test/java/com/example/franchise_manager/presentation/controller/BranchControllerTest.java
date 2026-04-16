package com.example.franchise_manager.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.franchise_manager.application.branch.dto.CreateBranchRequest;
import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.FranchiseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BranchController.class)
public class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BranchRepository branchRepository;

    @MockBean
    private FranchiseRepository franchiseRepository;

    @Test
    void save_ShouldReturnCreatedBranch() throws Exception {
        // Arrange
        Long franchiseId = 1L;
        CreateBranchRequest request = new CreateBranchRequest();
        request.setName("New Branch");

        Franchise franchise = new Franchise(franchiseId, "Franchise 1");
        Branch branch = new Branch(1L, "New Branch", franchise);

        when(franchiseRepository.findById(franchiseId)).thenReturn(franchise);
        when(branchRepository.save(any(Branch.class), eq(franchiseId))).thenReturn(branch);

        // Act & Assert
        mockMvc.perform(post("/branches/{franchiseId}", franchiseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Branch"));
    }

    @Test
    void updateBranch_ShouldReturnUpdatedBranch() throws Exception {
        // Arrange
        Long branchId = 1L;
        CreateBranchRequest request = new CreateBranchRequest();
        request.setName("Updated Branch");

        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(branchId, "Updated Branch", franchise);
        // Add a mock product since the response includes products
        com.example.franchise_manager.domain.model.Product product = 
            new com.example.franchise_manager.domain.model.Product(1L, "Product A", 10, branch);
        branch.addProduct(product);

        when(branchRepository.findById(branchId)).thenReturn(branch);
        when(branchRepository.update(branchId, "Updated Branch")).thenReturn(branch);

        // Act & Assert
        mockMvc.perform(patch("/branches/{branchId}", branchId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Branch"))
                .andExpect(jsonPath("$.products[0].id").value(1L))
                .andExpect(jsonPath("$.products[0].name").value("Product A"));
    }
}
