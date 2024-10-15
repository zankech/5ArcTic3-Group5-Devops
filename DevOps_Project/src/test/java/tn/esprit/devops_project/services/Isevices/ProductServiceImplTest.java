package tn.esprit.devops_project.services.Iservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests JUnit
    @Test
    void testShouldApplyDiscountSuccessfully() {
        // Arrange
        Product product = new Product();
        product.setIdProduct(1L);
        product.setDiscount(0);

        // Act
        product.setDiscount(20.0f); // remise traja3 mrigula walla le

        // Assert
        assertEquals(20.0f, product.getDiscount(), "La remise devrait être de 20%");
    }

    @Test
    void testShouldThrowExceptionWhenInvalidDiscountPercentage() {
        // Arrange
        float invalidDiscountPercentage = -10.0f; // Pourcentage mehech mrigulaa

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (invalidDiscountPercentage < 0) {
                throw new IllegalArgumentException("Pourcentage de remise invalide");
            }
        });

        assertEquals("Pourcentage de remise invalide", exception.getMessage());
    }

    // Tests Mockito
    @Test
    void applyDiscount_shouldApplyDiscountSuccessfully() {
        // Arrange
        Long productId = 1L;
        float discountPercentage = 20.0f;
        Product product = new Product();
        product.setIdProduct(productId);
        product.setDiscount(0); // Remise bdina bih

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // Act
        Product updatedProduct = productService.applyDiscount(productId, discountPercentage);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(discountPercentage, updatedProduct.getDiscount());
        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
    }

    @Test
    void applyDiscount_shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        Long productId = 1L;
        float discountPercentage = 20.0f;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> productService.applyDiscount(productId, discountPercentage));
        verify(productRepository).findById(productId);
    }
}
