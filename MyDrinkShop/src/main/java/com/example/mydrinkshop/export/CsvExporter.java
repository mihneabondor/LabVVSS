package com.example.mydrinkshop.export;

import com.example.mydrinkshop.domain.Order;
import com.example.mydrinkshop.domain.OrderItem;
import com.example.mydrinkshop.domain.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvExporter {
    public static void exportOrders(List<Product> products, List<Order> orders, String path) {
        try (FileWriter w = new FileWriter(path)) {
            w.write("OrderId,Product,Quantity,Price\n");
            double sum=0.0;
            for (Order o : orders){
                for (OrderItem i : o.getItems()) {
                    Product p = products.stream().filter((p1)->i.getProduct().getId()==p1.getId()).toList().get(0);
                    w.write(o.getId() + "," + p.getNume() + "," + i.getQuantity() + "," + i.getTotal() + "\n");
                }
                w.write("total order: "+o.getTotal()+" RON\n");
                w.write("-------------------------------\n");
                sum+=o.getTotal();
            }
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            w.write("TOTAL OF "+date+" is: "+sum+" RON\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}