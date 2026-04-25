package drinkshop.domain;

import com.example.mydrinkshop.domain.Order;
import com.example.mydrinkshop.domain.OrderItem;
import com.example.mydrinkshop.domain.Product;
import com.example.mydrinkshop.repository.Repository;
import com.example.mydrinkshop.repository.file.FileOrderRepository;
import com.example.mydrinkshop.repository.file.FileProductRepository;
import com.example.mydrinkshop.service.OrderService;
import com.example.mydrinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderService - addOrder() ECP Tests")
public class OrderAddTest {

    private OrderService orderService;
    private Repository<Integer, Product> productRepo;
    private Repository<Integer, Order> orderRepo;

    // Must exist in data/products.txt
    private static final int VALID_PRODUCT_ID = 101;

    @BeforeEach
    public void setUp() {
        productRepo  = new FileProductRepository("data/products.txt");
        orderRepo    = new FileOrderRepository("data/orders.txt", productRepo);
        orderService = new OrderService(orderRepo, productRepo);
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC1 ECP: Add valid order (id>0, non-empty items, totalPrice>=0)")
    @Timeout(1)
    public void testAddOrder_ValidData_ECP() {
        Product product  = productRepo.findOne(VALID_PRODUCT_ID);
        OrderItem item   = new OrderItem(product, 2);
        Order validOrder = new Order(9901, List.of(item), 20.0);

        int sizeBefore = orderService.getAllOrders().size();
        orderService.addOrder(validOrder);

        assertEquals(sizeBefore + 1, orderService.getAllOrders().size(),
                "Valid order should be added successfully.");
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC2 ECP: Add order with id=0 (invalid)")
    @Timeout(1)
    public void testAddOrder_IdZero_ECP() {
        Product product    = productRepo.findOne(VALID_PRODUCT_ID);
        OrderItem item     = new OrderItem(product, 1);
        Order invalidOrder = new Order(0, List.of(item), 10.0);

        assertThrows(ValidationException.class, () ->
                        orderService.addOrder(invalidOrder),
                "Order with id=0 should throw ValidationException.");
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC3 ECP: Add order with id=-1 (invalid)")
    @Timeout(1)
    public void testAddOrder_IdNegative_ECP() {
        Product product    = productRepo.findOne(VALID_PRODUCT_ID);
        OrderItem item     = new OrderItem(product, 1);
        Order invalidOrder = new Order(-1, List.of(item), 10.0);

        assertThrows(ValidationException.class, () ->
                        orderService.addOrder(invalidOrder),
                "Order with negative id should throw ValidationException.");
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC4 ECP: Add order with empty items list (invalid)")
    @Timeout(1)
    public void testAddOrder_EmptyItems_ECP() {
        Order invalidOrder = new Order(9902, List.of(), 0.0);

        assertThrows(ValidationException.class, () ->
                        orderService.addOrder(invalidOrder),
                "Order with empty items list should throw ValidationException.");
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC5 ECP: Add order with item quantity=0 (invalid)")
    @Timeout(1)
    public void testAddOrder_ItemQuantityZero_ECP() {
        Product product    = productRepo.findOne(VALID_PRODUCT_ID);
        OrderItem item     = new OrderItem(product, 0);
        Order invalidOrder = new Order(9903, List.of(item), 0.0);

        assertThrows(ValidationException.class, () ->
                        orderService.addOrder(invalidOrder),
                "Order with item quantity=0 should throw ValidationException.");
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC6 ECP: Add order with item quantity=-3 (invalid)")
    @Timeout(1)
    public void testAddOrder_ItemQuantityNegative_ECP() {
        Product product    = productRepo.findOne(VALID_PRODUCT_ID);
        OrderItem item     = new OrderItem(product, -3);
        Order invalidOrder = new Order(9904, List.of(item), 0.0);

        assertThrows(ValidationException.class, () ->
                        orderService.addOrder(invalidOrder),
                "Order with negative item quantity should throw ValidationException.");
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC7 ECP: Add order with totalPrice=0.0 (valid)")
    @Timeout(1)
    public void testAddOrder_TotalPriceZero_ECP() {
        Product product  = productRepo.findOne(VALID_PRODUCT_ID);
        OrderItem item   = new OrderItem(product, 1);
        Order validOrder = new Order(9905, List.of(item), 0.0);

        int sizeBefore = orderService.getAllOrders().size();
        orderService.addOrder(validOrder);

        assertEquals(sizeBefore + 1, orderService.getAllOrders().size(),
                "Order with totalPrice=0.0 should be valid and added.");
    }

    @Test
    @Tag("ECP")
    @DisplayName("TC8 ECP: Add order with totalPrice=-5.0 (invalid)")
    @Timeout(1)
    public void testAddOrder_TotalPriceNegative_ECP() {
        Product product    = productRepo.findOne(VALID_PRODUCT_ID);
        OrderItem item     = new OrderItem(product, 1);
        Order invalidOrder = new Order(9906, List.of(item), -5.0);

        assertThrows(ValidationException.class, () ->
                        orderService.addOrder(invalidOrder),
                "Order with negative totalPrice should throw ValidationException.");
    }

    @AfterEach
    public void tearDown() {
        int[] testIds = {9901, 9902, 9903, 9904, 9905, 9906};
        for (int id : testIds) {
            orderRepo.delete(id);
        }
    }
}