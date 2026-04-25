package com.example.mydrinkshop.reports;

import com.example.mydrinkshop.domain.Order;
import com.example.mydrinkshop.repository.Repository;

public class DailyReportService {
    private Repository<Integer, Order> repo;

    public DailyReportService(Repository<Integer, Order> repo) {
        this.repo = repo;
    }

    public double getTotalRevenue() {
        return repo.findAll().stream().mapToDouble(Order::getTotal).sum();
    }

    public int getTotalOrders() {
//        List<Order> orders = StreamSupport.stream(repo.findAll().spliterator(), false)
//                .collect(Collectors.toList());

        return repo.findAll().size();
    }
}