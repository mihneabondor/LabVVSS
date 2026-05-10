package drinkshop.domain;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductAddTest {
    private ProductService productService;
    private File tempFile; // <-- Variabilă nouă pentru fișierul temporar


    @BeforeEach
    public void setUp() throws IOException {
        // Creăm un fișier temporar gol, izolat pentru fiecare test în parte
        tempFile = File.createTempFile("test_products_unit", ".txt");
        tempFile.deleteOnExit();

        Repository<Integer, Product> productRepo = new FileProductRepository(tempFile.getAbsolutePath());
        productService = new ProductService(productRepo);
    }

    @AfterEach
    public void tearDown() {
        // Curățăm fișierul după ce se termină testul, ca să nu afecteze alte teste
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC1 ECP: Add Valid Product")
    @Timeout(1)
    public void testAddProduct_ValidData_ECP() {

        Product produsValid = new Product(102, "Ananas", 10.0, CategorieBautura.JUICE, TipBautura.DAIRY);
        int dimensiuneInitiala = productService.getAllProducts().size();

        productService.addProduct(produsValid);


        assertEquals(dimensiuneInitiala + 1, productService.getAllProducts().size(), "Valid Product should be added with succes!");
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC2 ECP: Add Product with Invalid Name")
    @Timeout(1)
    public void testAddProduct_InvalidName_ECP() {

        Product produsNumeInvalid = new Product(2, "Mere", 10.0,CategorieBautura.JUICE, TipBautura.DAIRY);

        assertThrows(ValidationException.class, () -> {
            productService.addProduct(produsNumeInvalid);
        }, "Name is too short, at least 6 characters!");
    }
    @Test
    @Tag("ECP")
    @DisplayName("TC3 ECP: Add Product with Invalid Price")
    @Timeout(1)
    public void testAddProduct_InvalidPrice_ECP() {
        Product produsPretInvalid = new Product(3, "Ananas", -20.0,CategorieBautura.JUICE, TipBautura.DAIRY);

        assertThrows(ValidationException.class, () -> {
            productService.addProduct(produsPretInvalid);
        }, "Price with negative value or 0, not ok!");
    }

    // ==================================================


    @Test
    @Tag("BVA")
    @DisplayName("TC1 BVA: Name length 6 (Valid)")
    @Timeout(1)
    public void testAddProduct_NameLen6_BVA() {

        Product p = new Product(101, "Ananas", 10.0, CategorieBautura.JUICE, TipBautura.DAIRY);
        int sizeBefore = productService.getAllProducts().size();


        productService.addProduct(p);


        assertEquals(sizeBefore + 1, productService.getAllProducts().size(), "Should add product with 6 chars name.");
    }

    @Test
    @Tag("BVA")
    @DisplayName("TC2 BVA: Name length 5 (Invalid)")
    @Timeout(1)
    public void testAddProduct_NameLen5_BVA() {
        Product p = new Product(102, "Anana", 10.0, CategorieBautura.JUICE, TipBautura.DAIRY);

        assertThrows(ValidationException.class, () -> {
            productService.addProduct(p);
        }, "Should throw exception for 5 chars name.");
    }

    @Test
    @Tag("BVA")
    @DisplayName("TC3 BVA: Name length 4 (Invalid)")
    @Timeout(1)
    public void testAddProduct_NameLen4_BVA() {

        Product p = new Product(103, "Anan", 10.0, CategorieBautura.JUICE, TipBautura.DAIRY);


        assertThrows(ValidationException.class, () -> {
            productService.addProduct(p);
        }, "Should throw exception for 4 chars name.");
    }



    @Test
    @Tag("BVA")
    @DisplayName("TC4 BVA: Price 0.01 (Valid)")
    @Timeout(1)
    public void testAddProduct_Price001_BVA() {

        Product p = new Product(104, "Ananas", 0.01, CategorieBautura.JUICE, TipBautura.DAIRY);
        int sizeBefore = productService.getAllProducts().size();


        productService.addProduct(p);


        assertEquals(sizeBefore + 1, productService.getAllProducts().size(), "Should add product with price 0.01.");
    }

    @Test
    @Tag("BVA")
    @DisplayName("TC5 BVA: Price 0.00 (Invalid)")
    @Timeout(1)
    public void testAddProduct_Price000_BVA() {

        Product p = new Product(105, "Ananas", 0.00, CategorieBautura.JUICE, TipBautura.DAIRY);


        assertThrows(ValidationException.class, () -> {
            productService.addProduct(p);
        }, "Should throw exception for price 0.00.");
    }

    @Test
    @Tag("BVA")
    @DisplayName("TC6 BVA: Price -0.01 (Invalid)")
    @Timeout(1)
    public void testAddProduct_PriceMinus001_BVA() {

        Product p = new Product(106, "Ananas", -0.01, CategorieBautura.JUICE, TipBautura.DAIRY);


        assertThrows(ValidationException.class, () -> {
            productService.addProduct(p);
        }, "Should throw exception for price -0.01.");
    }
}
