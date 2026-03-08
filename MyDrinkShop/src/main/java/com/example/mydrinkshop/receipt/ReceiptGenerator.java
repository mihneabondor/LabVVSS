package com.example.mydrinkshop.receipt;

import com.example.mydrinkshop.domain.Order;
import com.example.mydrinkshop.domain.OrderItem;
import com.example.mydrinkshop.domain.Product;

import java.util.List;

public class ReceiptGenerator {
    public static String generate(Order o, List<Product> products) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== BON FISCAL =====\n").append("Comanda #").append(o.getId()).append("\n");
        for (OrderItem i : o.getItems()) {
            Product p = products.stream().filter((p1)->i.getProduct().getId()==p1.getId()).toList().get(0);
            sb.append(p.getNume()).append(": ").append(p.getPret()).append(" x ").append(i.getQuantity()).append(" = ").append(i.getTotal()).append(" RON\n");
        }
        sb.append("---------------------\nTOTAL: ").append(o.getTotalPrice()).append(" RON\n=====================\n");
        return sb.toString();
    }
}