package com.example.mydrinkshop.service.validator;

import com.example.mydrinkshop.domain.Order;
import com.example.mydrinkshop.domain.OrderItem;

public class OrderValidator implements Validator<Order> {

    private final OrderItemValidator itemValidator = new OrderItemValidator();

    @Override
    public void validate(Order order) {

        String errors = "";

        if (order.getId() <= 0)
            errors += "ID comanda invalid!\n";

        if (order.getItems() == null || order.getItems().isEmpty())
            errors += "Comanda fara produse!\n";

        for (OrderItem item : order.getItems()) {
            try {
                itemValidator.validate(item);
            } catch (ValidationException e) {
                errors += e.getMessage();
            }
        }

        if (order.getTotalPrice() < 0)
            errors += "Total invalid!\n";

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
