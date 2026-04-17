package com.example.franchise_manager.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.franchise_manager.application.product.dto.CreateProductRequest;
import com.example.franchise_manager.application.product.dto.ProductResponse;
import com.example.franchise_manager.application.product.dto.UpdateProductNameRequest;
import com.example.franchise_manager.application.product.dto.UpdateProductStockRequest;
import com.example.franchise_manager.domain.model.Branch;
import com.example.franchise_manager.domain.model.Franchise;
import com.example.franchise_manager.domain.model.Product;
import com.example.franchise_manager.domain.repository.BranchRepository;
import com.example.franchise_manager.domain.repository.ProductRepository;
import com.example.franchise_manager.presentation.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private BranchRepository branchRepository;

    @MockBean
    private ProductMapper productMapper;

    @Test
    void getMethodName_ShouldReturnEmptyString() throws Exception {
        mockMvc.perform(get("/products/path")
                .param("param", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void save_ShouldReturnCreatedProduct() throws Exception {
        // Arrange
        Long branchId = 1L;
        CreateProductRequest request = new CreateProductRequest();
        request.setName("New Product");
        request.setStock(50);

        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(branchId, "Branch 1", franchise);
        Product product = new Product(1L, "New Product", 50, branch);

        when(branchRepository.findById(branchId)).thenReturn(branch);
        when(productRepository.save(any(Product.class), eq(branchId))).thenReturn(product);

        ProductResponse response = new ProductResponse(1L, "New Product", 50);
        when(productMapper.productToProductResponse(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/products/{branchId}", branchId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.stock").value(50));
    }

    @Test
    void updateStock_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        Long productId = 1L;
        UpdateProductStockRequest request = new UpdateProductStockRequest();
        request.setStock(100);

        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(1L, "Branch 1", franchise);
        Product product = new Product(productId, "Product 1", 50, branch);
        Product updatedProduct = new Product(productId, "Product 1", 100, branch);

        when(productRepository.findById(productId)).thenReturn(product);
        when(productRepository.updateStock(productId, 100)).thenReturn(updatedProduct);

        ProductResponse response = new ProductResponse(1L, "Product 1", 100);
        when(productMapper.productToProductResponse(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(patch("/products/{productId}/stock", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.stock").value(100));
    }

    @Test
    void updateName_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        Long productId = 1L;
        UpdateProductNameRequest request = new UpdateProductNameRequest();
        request.setName("Updated Product");

        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(1L, "Branch 1", franchise);
        Product product = new Product(productId, "Old Name", 50, branch);
        Product updatedProduct = new Product(productId, "Updated Product", 50, branch);

        when(productRepository.findById(productId)).thenReturn(product);
        when(productRepository.updateName(productId, "Updated Product")).thenReturn(updatedProduct);

        ProductResponse response = new ProductResponse(1L, "Updated Product", 50);
        when(productMapper.productToProductResponse(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(patch("/products/{productId}/name", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Product"));
    }

    @Test
    void delete_ShouldReturnSuccessMessage() throws Exception {
        // Arrange
        Long productId = 1L;
        Long branchId = 1L;

        Franchise franchise = new Franchise(1L, "Franchise 1");
        Branch branch = new Branch(branchId, "Branch 1", franchise);
        Product product = new Product(productId, "Product 1", 50, branch);
        branch.addProduct(product);

        when(productRepository.findById(productId)).thenReturn(product);
        when(branchRepository.findById(branchId)).thenReturn(branch);

        // Act & Assert
        mockMvc.perform(delete("/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));
    }
}
