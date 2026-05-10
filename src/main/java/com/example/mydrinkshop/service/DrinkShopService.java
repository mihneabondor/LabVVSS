package com.example.mydrinkshop.service;

import com.example.mydrinkshop.domain.*;
import com.example.mydrinkshop.domain.*;
import com.example.mydrinkshop.export.CsvExporter;
import com.example.mydrinkshop.receipt.ReceiptGenerator;
import com.example.mydrinkshop.reports.DailyReportService;
import com.example.mydrinkshop.repository.Repository;

import java.util.List;

public class DrinkShopService {

    private final ProductService productService;
    private final OrderService orderService;
    private final RetetaService retetaService;
    private final StocService stocService;
    private final DailyReportService report;

    public DrinkShopService(
            Repository<Integer, Product> productRepo,
            Repository<Integer, Order> orderRepo,
            Repository<Integer, Reteta> retetaRepo,
            Repository<Integer, Stoc> stocService
    ) {
        this.productService = new ProductService(productRepo);
        this.orderService = new OrderService(orderRepo, productRepo);
        this.retetaService = new RetetaService(retetaRepo);
        this.stocService = new StocService(stocService);
        this.report = new DailyReportService(orderRepo);
    }

    // ---------- PRODUCT ----------
    public void addProduct(Product p) {
        productService.addProduct(p);
    }

    public void updateProduct(int id, String name, double price, CategorieBautura categorie, TipBautura tip) {
        productService.updateProduct(id, name, price, categorie, tip);
    }

    public void deleteProduct(int id) {
        productService.deleteProduct(id);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public List<Product> filtreazaDupaCategorie(CategorieBautura categorie) {
        return productService.filterByCategorie(categorie);
    }

    public List<Product> filtreazaDupaTip(TipBautura tip) {
        return productService.filterByTip(tip);
    }

    // ---------- ORDER ----------
    public void addOrder(Order o) {
        orderService.addOrder(o);
    }

    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    public double computeTotal(Order o) {
        return orderService.computeTotal(o);
    }

    public String generateReceipt(Order o) {
        return ReceiptGenerator.generate(o, productService.getAllProducts());
    }

    public double getDailyRevenue() {
        return report.getTotalRevenue();
    }

    public void exportCsv(String path) {
        CsvExporter.exportOrders(productService.getAllProducts(), orderService.getAllOrders(), path);
    }

    // ---------- STOCK + RECIPE ----------
    public void comandaProdus(Product produs) {
        Reteta reteta = retetaService.findById(produs.getId());

        if (!stocService.areSuficient(reteta)) {
            throw new IllegalStateException("Stoc insuficient pentru produsul: " + produs.getNume());
        }
        stocService.consuma(reteta);
    }

    public List<Reteta> getAllRetete() {
        return retetaService.getAll();
    }

    public void addReteta(Reteta r) {
        retetaService.addReteta(r);
    }

    public void updateReteta(Reteta r) {
        retetaService.updateReteta(r);
    }

    public void deleteReteta(int id) {
        retetaService.deleteReteta(id);
    }
}