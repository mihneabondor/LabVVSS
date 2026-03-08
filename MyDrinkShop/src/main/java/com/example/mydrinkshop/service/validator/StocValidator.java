package com.example.mydrinkshop.service.validator;

import com.example.mydrinkshop.domain.Stoc;

public class StocValidator implements Validator<Stoc> {

    @Override
    public void validate(Stoc stoc) {

        String errors = "";

        if (stoc.getId() <= 0)
            errors += "ID invalid!\n";

        if (stoc.getIngredient() == null || stoc.getIngredient().isBlank())
            errors += "Ingredient invalid!\n";

        if (stoc.getCantitate() < 0)
            errors += "Cantitate negativa!\n";

        if (stoc.getStocMinim() < 0)
            errors += "Stoc minim negativ!\n";

        if (stoc.getCantitate() < stoc.getStocMinim())
            errors += "Cantitatea este sub stocul minim!\n";

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}