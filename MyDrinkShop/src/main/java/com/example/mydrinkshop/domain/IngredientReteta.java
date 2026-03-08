package com.example.mydrinkshop.domain;

public class IngredientReteta {

    private String denumire;
    private double cantitate;

    public IngredientReteta(String denumire, double cantitate) {
        this.denumire = denumire;
        this.cantitate = cantitate;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return denumire + "," + cantitate;
    }
}