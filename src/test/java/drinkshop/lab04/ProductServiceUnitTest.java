package drinkshop.lab04;

import com.example.mydrinkshop.domain.CategorieBautura;
import com.example.mydrinkshop.domain.Product;
import com.example.mydrinkshop.domain.TipBautura;
import com.example.mydrinkshop.repository.Repository;
import com.example.mydrinkshop.service.ProductService;
import com.example.mydrinkshop.service.validator.ProductValidator;
import com.example.mydrinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Step 1 - Unit Testing (izolare) pentru ProductService.
 * Scenariul (1): V <--- S ---> R
 * Se folosesc obiecte mock pentru ProductValidator si Repository.
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @Mock
    private Repository<Integer, Product> mockRepository;

    @Mock
    private ProductValidator mockValidator;

    private ProductService productService;

    private Product validProduct;
    private Product invalidProduct;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(mockRepository, mockValidator);

        validProduct = new Product(1, "Espresso", 10.0, CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC);
        invalidProduct = new Product(2, "Tea", -5.0, CategorieBautura.TEA, TipBautura.WATER_BASED);
    }



    @Test
    @DisplayName("TC1 - addProduct")
    public void testAddProduct_ValidProduct_SavedInRepo() {
        // Arrange
        when(mockRepository.save(validProduct)).thenReturn(validProduct);

        // Act
        productService.addProduct(validProduct);

        // Assert
        verify(mockRepository, times(1)).save(validProduct);
    }

    @Test
    @DisplayName("TC2 - addProduct: arunca ValidationException")
    public void testAddProduct_NegativePrice_ThrowsValidationException() {
        // Arrange
        doThrow(new ValidationException("Pret invalid!"))
                .when(mockValidator).validate(invalidProduct);

        // Act & Assert
        assertThrows(ValidationException.class, () -> {
            productService.addProduct(invalidProduct);
        }, "Ar trebui sa arunce ValidationException pentru pret negativ");

        // Verify
        verify(mockRepository, never()).save(any());
    }

    @Test
    @DisplayName("TC3 - getAllProducts")
    public void testGetAllProducts_ReturnsListFromRepo() {
        // Arrange
        List<Product> expectedList = Collections.singletonList(validProduct);
        when(mockRepository.findAll()).thenReturn(expectedList);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(1, result.size());
        assertEquals(validProduct, result.get(0));

        // Verify
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TC4 - deleteProduct")
    public void testDeleteProduct_CallsRepoDeleteWithCorrectId() {
        // Arrange
        when(mockRepository.delete(1)).thenReturn(validProduct);

        // Act
        productService.deleteProduct(1);

        // Assert + Verify
        verify(mockRepository, times(1)).delete(1);
        verify(mockRepository, never()).delete(2);
    }

    @Test
    @DisplayName("TC5 - findById")
    public void testFindById_ReturnsProductFromRepo() {
        // Arrange
        when(mockRepository.findOne(1)).thenReturn(validProduct);

        // Act
        Product result = productService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());

        // Verify
        verify(mockRepository, times(1)).findOne(1);
    }

    @Test
    @DisplayName("TC6 - addProduct: arunca ValidationException")
    public void testAddProduct_ShortName_ThrowsValidationException() {
        // Arrange
        Product shortNameProduct = new Product(4, "Cafe", 8.0,
                CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC);

        doThrow(new ValidationException("Nume prea scurt!"))
                .when(mockValidator).validate(shortNameProduct);

        // Act & Assert
        assertThrows(ValidationException.class, () -> {
            productService.addProduct(shortNameProduct);
        }, "Ar trebui sa arunce ValidationException pentru nume prea scurt");

        // Verify
        verify(mockRepository, never()).save(any());
    }
}
