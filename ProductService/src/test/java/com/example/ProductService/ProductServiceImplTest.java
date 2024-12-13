package com.example.ProductService;

import com.example.ProductService.entity.Product;
import com.example.ProductService.model.ProductRequest;
import com.example.ProductService.repository.ProductRepository;
import com.example.ProductService.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    @Test
    void addProducts_shouldSaveProductsSuccessfully() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("Netflix");
        productRequest.setQuantity(1);
        productRequest.setPrice(1000);

        // Create a product with an ID explicitly set to 1
        Product product = Product.builder()
                .id(20L)  // Set the ID explicitly
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();

        // Mock the behavior of productRepository.save()
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Call the method under test
        long productId = productService.addProduct(productRequest);

        // Assert the product ID matches
        assertEquals(product.getId(), productId);

        // Verify that the save method was called exactly once
        verify(productRepository, times(1)).save(any(Product.class));
    }
    @Test
    void reduceQuantity_shouldReduceQuantitySuccessfully() {
        // Arrange
        Product product = Product.builder()
                .id(1L)
                .productName("Test Product")
                .price(100)
                .quantity(10)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        productService.reduceQuantity(1L, 5);

        // Assert
        assertEquals(5, product.getQuantity());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

}
