package com.example.mydrinkshop.domain;

import java.util.List;

public class Reteta {

    private int id;
    private List<IngredientReteta> ingrediente;

    public Reteta(int id, List<IngredientReteta> ingrediente) {
        this.id = id;
        this.ingrediente = ingrediente;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public List<IngredientReteta> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(List<IngredientReteta> ingrediente) {
        this.ingrediente = ingrediente;
    }

    @Override
    public String toString() {
        return "Reteta{" +
                "productId=" + id +
                ", ingrediente=" + ingrediente +
                '}';
    }
}

