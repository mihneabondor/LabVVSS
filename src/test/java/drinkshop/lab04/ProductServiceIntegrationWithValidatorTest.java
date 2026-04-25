package drinkshop.lab04;

import com.example.mydrinkshop.domain.CategorieBautura;
import com.example.mydrinkshop.domain.Product;
import com.example.mydrinkshop.domain.TipBautura;
import com.example.mydrinkshop.repository.Repository;
import com.example.mydrinkshop.service.ProductService;
import com.example.mydrinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Step 2 - Integration Testing: S + V (real ProductValidator integrat).
 * Scenariul (1) breadth first: V <--- S ---> R
 * Repository ramane mock. Validatorul este cel real (integrat).
 *
 * ProductService creeaza intern un ProductValidator real,
 * deci testam comportamentul real al validarii integrate cu serviciul.
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceIntegrationWithValidatorTest {

    @Mock
    private Repository<Integer, Product> mockRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {

        productService = new ProductService(mockRepository);
    }



    @Test
    @DisplayName("TC1 - Integrare S+V: produs valid trece validarea si ajunge la repo")
    public void testAddProduct_ValidProduct_PassesRealValidation_AndReachesRepo() {
        // Arrange
        Product validProduct = new Product(10, "Espresso Lungo", 12.0,
                CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC);
        when(mockRepository.save(validProduct)).thenReturn(validProduct);

        // Act - validatorul real ruleaza, produsul e valid
        productService.addProduct(validProduct);

        // Assert
        verify(mockRepository, times(1)).save(validProduct);
    }

    @Test
    @DisplayName("TC2 - arunca Exceptie")
    public void testAddProduct_ZeroPrice_RealValidationThrows() {
        // Arrange
        Product zeroPriceProduct = new Product(11, "Espresso", 0.0,
                CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC);

        // Act & Assert
        assertThrows(ValidationException.class, () -> {
            productService.addProduct(zeroPriceProduct);
        }, "Validatorul real trebuie sa respinga pretul 0");

        // Verify
        verify(mockRepository, never()).save(any());
    }

    @Test
    @DisplayName("TC3 - Integrare S+V: produs cu ID invalid este respins de validator")
    public void testAddProduct_InvalidId_RealValidationThrows() {
        // Arrange
        Product invalidIdProduct = new Product(0, "Espresso", 10.0,
                CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC);

        // Act & Assert
        assertThrows(ValidationException.class, () -> {
            productService.addProduct(invalidIdProduct);
        }, "Validatorul real trebuie sa respinga ID-ul 0");

        verify(mockRepository, never()).save(any());
    }

    @Test
    @DisplayName("TC4 - Integrare S+V: mesajul exceptiei contine detalii relevante")
    public void testAddProduct_InvalidData_ExceptionMessageContainsDetails() {
        // Arrange
        Product badProduct = new Product(5, "Tea", -10.0,
                CategorieBautura.TEA, TipBautura.WATER_BASED);

        // Act
        ValidationException ex = assertThrows(ValidationException.class, () -> {
            productService.addProduct(badProduct);
        });

        // Assert
        assertNotNull(ex.getMessage(), "Mesajul exceptiei nu trebuie sa fie null");
        assertFalse(ex.getMessage().isBlank(), "Mesajul exceptiei nu trebuie sa fie gol");
    }


}
