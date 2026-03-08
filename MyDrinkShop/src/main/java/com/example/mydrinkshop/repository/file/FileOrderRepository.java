package com.example.mydrinkshop.repository.file;

import com.example.mydrinkshop.domain.Order;
import com.example.mydrinkshop.domain.OrderItem;
import com.example.mydrinkshop.domain.Product;
import com.example.mydrinkshop.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class FileOrderRepository
        extends FileAbstractRepository<Integer, Order> {

    private Repository<Integer, Product> productRepository;

    public FileOrderRepository(String fileName, Repository<Integer, Product> productRepository) {
        super(fileName);
        this.productRepository = productRepository;
        loadFromFile();
    }

    @Override
    protected Integer getId(Order entity) {
        return entity.getId();
    }

    @Override
    protected Order extractEntity(String line) {

        // Format: id,productId:qty|productId:qty,total
        String[] parts = line.split(",");

        int id = Integer.parseInt(parts[0]);

        List<OrderItem> items = new ArrayList<>();
        String[] products = parts[1].split("\\|");

        for (String product : products) {
            String[] prodParts = product.split(":");

            int productId = Integer.parseInt(prodParts[0]);
            int quantity = Integer.parseInt(prodParts[1]);

            items.add(new OrderItem(productRepository.findOne(productId), quantity));
        }

        double totalPrice = Double.parseDouble(parts[2]);

        return new Order(id, items, totalPrice);
    }

    @Override
    protected String createEntityAsString(Order entity) {

        StringBuilder sb = new StringBuilder();

        for (OrderItem item : entity.getItems()) {

            if (sb.length() > 0) {
                sb.append("|");
            }

            sb.append(item.getProduct().getId())
                    .append(":")
                    .append(item.getQuantity());
        }

        return entity.getId() + "," +
                sb + "," +
                entity.getTotalPrice();
    }
}
