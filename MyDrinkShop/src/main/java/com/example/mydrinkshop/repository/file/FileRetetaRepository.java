package com.example.mydrinkshop.repository.file;

import com.example.mydrinkshop.domain.IngredientReteta;
import com.example.mydrinkshop.domain.Reteta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileRetetaRepository
        extends FileAbstractRepository<Integer, Reteta> {

    public FileRetetaRepository(String fileName) {
        super(fileName);
        loadFromFile();
    }

    @Override
    protected Integer getId(Reteta entity) {
        return entity.getId();
    }

    @Override
    protected Reteta extractEntity(String line) {

        String[] elems = line.split(",");

        int productId = Integer.parseInt(elems[0]);
        List<IngredientReteta> ingrediente = new ArrayList<>();
        int index=1;
        while (index<elems.length) {
            String ingredientTotal= elems[index++];
            String[] ingredientSeparat = ingredientTotal.split(":");
            String ingredientName = ingredientSeparat[0];
            Double ingredientQuantity = Double.parseDouble(ingredientSeparat[1]);
            ingrediente.add(new IngredientReteta(ingredientName, ingredientQuantity));
        }
        return new Reteta(productId, ingrediente);
    }

    @Override
    protected String createEntityAsString(Reteta entity) {
        String ingrediente = entity.getIngrediente().stream()
                        .map(entry -> entry.getDenumire() + ":" + entry.getCantitate())
                        .collect(Collectors.joining(","));
        return entity.getId() + "," +
                ingrediente;
    }
}
