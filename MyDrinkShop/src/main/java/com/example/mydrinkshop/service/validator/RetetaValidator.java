package com.example.mydrinkshop.service.validator;

import com.example.mydrinkshop.domain.IngredientReteta;
import com.example.mydrinkshop.domain.Reteta;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RetetaValidator implements Validator<Reteta> {

    @Override
    public void validate(Reteta reteta) {

        AtomicReference<String> errors = new AtomicReference<>("");

        if (reteta.getId() <= 0)
            errors.accumulateAndGet("Product ID invalid!\n", String::concat);

        List<IngredientReteta> ingrediente = reteta.getIngrediente();
        if (ingrediente == null || ingrediente.isEmpty())
            errors.accumulateAndGet("Ingrediente empty!\n", String::concat);

        ingrediente.stream()
                .filter(entry -> entry.getCantitate() <= 0)
                .forEach(entry -> {
                    errors.accumulateAndGet("[" + entry.getDenumire() + "]"+ "cantitate negativa sau zero", String::concat);
                });

        if (!errors.get().isEmpty())
            throw new ValidationException(errors.get());
    }
}
