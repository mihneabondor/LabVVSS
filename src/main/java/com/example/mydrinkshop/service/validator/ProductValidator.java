package com.example.mydrinkshop.service.validator;

import com.example.mydrinkshop.domain.Product;

public class ProductValidator implements Validator<Product> {

    @Override
    public void validate(Product product) {

        String errors = "";

        if(product.getPret() <= 0.0)
            errors += "Pretul trebuie sa fie pozitiv!\n";
        if(product.getNume().length() < 6)
            errors += "Numele trebuie sa aiba cel putin 6 caractere!\n";
        if (!(product.getNume() instanceof String))
            errors += "Numele trebuie sa fie un string!\n";{
        }
        if (product.getId() <= 0)
            errors += "ID invalid!\n";

        if (product.getNume() == null || product.getNume().isBlank())
            errors += "Numele nu poate fi gol!\n";

        if (product.getPret() <= 0)
            errors += "Pret invalid!\n";

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
