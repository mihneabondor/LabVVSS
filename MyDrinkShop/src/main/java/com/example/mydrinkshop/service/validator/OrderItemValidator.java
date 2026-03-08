package com.example.mydrinkshop.service.validator;

import com.example.mydrinkshop.domain.OrderItem;

public class OrderItemValidator implements Validator<OrderItem> {

    @Override
    public void validate(OrderItem item) {

        String errors = "";

        if (item.getProduct().getId() <= 0)
            errors += "Product ID invalid!\n";

        if (item.getQuantity() <= 0)
            errors += "Cantitate invalida!\n";

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
