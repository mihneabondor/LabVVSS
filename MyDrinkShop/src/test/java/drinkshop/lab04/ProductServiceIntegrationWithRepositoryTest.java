package drinkshop.lab04;

import com.example.mydrinkshop.domain.CategorieBautura;
import com.example.mydrinkshop.domain.Product;
import com.example.mydrinkshop.domain.TipBautura;
import com.example.mydrinkshop.repository.Repository;
import com.example.mydrinkshop.repository.file.FileProductRepository;
import com.example.mydrinkshop.service.ProductService;
import com.example.mydrinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step 3 - Integration Testing: S + V + R (FileProductRepository real, integrat).
 * Scenariul (1) breadth first: V <--- S ---> R
 * Toti componentii reali sunt integrati (fara mock-uri).
 * Se foloseste un fisier temporar pentru testare.
 */
public class ProductServiceIntegrationWithRepositoryTest {

    private ProductService productService;
    private Repository<Integer, Product> realRepository;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = File.createTempFile("test_products", ".txt");
        tempFile.deleteOnExit();

        realRepository = new FileProductRepository(tempFile.getAbsolutePath());
        productService = new ProductService(realRepository);
    }

    @AfterEach
    public void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }


    @Test
    @DisplayName("TC1 - Integrare S+V+R: produs valid este salvat si regasit in repository")
    public void testAddProduct_ValidProduct_PersistsInRepository() {
        // Arrange
        Product p = new Product(1, "Espresso", 10.0,
                CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC);

        // Act
        productService.addProduct(p);

        // Assert - produsul e efectiv in repo
        List<Product> all = productService.getAllProducts();
        assertEquals(1, all.size(), "Repository-ul trebuie sa contina 1 produs");
        assertEquals(1, all.get(0).getId(), "ID-ul produsului trebuie sa fie 1");
    }

    @Test
    @DisplayName("TC2 - Integrare S+V+R: produs invalid nu ajunge in repository")
    public void testAddProduct_InvalidProduct_NotSavedInRepository() {
        // Arrange
        Product invalidProduct = new Product(2, "Tea", -5.0,
                CategorieBautura.TEA, TipBautura.WATER_BASED);

        // Act & Assert
        assertThrows(ValidationException.class, () -> {
            productService.addProduct(invalidProduct);
        });

        // Verify - repository ramane gol
        List<Product> all = productService.getAllProducts();
        assertEquals(0, all.size(), "Repository-ul trebuie sa ramana gol dupa esec");
    }

    @Test
    @DisplayName("TC3 - Integrare S+V+R: adaugare multipla de produse valide")
    public void testAddMultipleProducts_AllValid_AllSaved() {
        // Arrange
        Product p1 = new Product(10, "Espresso", 8.0,
                CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC);
        Product p2 = new Product(11, "Cappuccino", 12.0,
                CategorieBautura.MILK_COFFEE, TipBautura.DAIRY);
        Product p3 = new Product(12, "Green Tea", 7.0,
                CategorieBautura.TEA, TipBautura.WATER_BASED);

        // Act
        productService.addProduct(p1);
        productService.addProduct(p2);
        productService.addProduct(p3);

        // Assert
        List<Product> all = productService.getAllProducts();
        assertEquals(3, all.size(), "Trebuie sa fie 3 produse in repository");
    }

    @Test
    @DisplayName("TC4 - Integrare S+V+R: stergere produs existent functioneaza")
    public void testDeleteProduct_ExistingProduct_RemovedFromRepo() {
        // Arrange
        Product p = new Product(20, "Americano", 9.0,
                CategorieBautura.CLASSIC_COFFEE, TipBautura.BASIC);
        productService.addProduct(p);
        assertEquals(1, productService.getAllProducts().size());

        // Act
        productService.deleteProduct(20);

        // Assert
        assertEquals(0, productService.getAllProducts().size(),
                "Repository-ul trebuie sa fie gol dupa stergere");
    }

}
