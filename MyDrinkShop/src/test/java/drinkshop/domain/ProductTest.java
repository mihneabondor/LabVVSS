package drinkshop.domain;

import com.example.mydrinkshop.domain.CategorieBautura;
import com.example.mydrinkshop.domain.Product;
import com.example.mydrinkshop.domain.TipBautura;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        product =new Product(100, "Limonada", 10.0, CategorieBautura.JUICE, TipBautura.WATER_BASED);
    }

    @AfterEach
    void tearDown() {
        product = null;
    }

    @Test
    void getId() {
        assert 100 == product.getId();
    }

    @Test
    void getNume() {
        assert "Limonada".equals(product.getNume());
    }

    @Test
    void getPret() {
        assert 10.0 == product.getPret();
    }

    @Test
    void getCategorie() {
        assert CategorieBautura.JUICE.equals(product.getCategorie());
    }

    @Test
    void setCategorie() {
        product.setCategorie(CategorieBautura.SMOOTHIE);
        assert CategorieBautura.SMOOTHIE.equals(product.getCategorie());
    }

    @Test
    void getTip() {
        assert TipBautura.WATER_BASED.equals(product.getTip());
    }

    @Test
    void setTip() {
        product.setTip(TipBautura.BASIC);
        assert TipBautura.BASIC.equals(product.getTip());
    }

    @Test
    void setNume() {
        product.setNume("newLimonada");
        assert "newLimonada".equals(product.getNume());
    }

    @Test
    void setPret() {
        product.setPret(10.05);
        assert 10.05 == product.getPret();
    }

    @Test
    void testToString() {
        System.out.println(product.toString());
        assert "Limonada (JUICE, WATER_BASED) - 10.0 lei".equals(product.toString());
    }
}