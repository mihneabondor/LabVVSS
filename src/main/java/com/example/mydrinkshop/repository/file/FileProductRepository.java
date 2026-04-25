package com.example.mydrinkshop.repository.file;

import com.example.mydrinkshop.domain.Product;
import com.example.mydrinkshop.domain.CategorieBautura;
import com.example.mydrinkshop.domain.TipBautura;

public class FileProductRepository
        extends FileAbstractRepository<Integer, Product> {

    public FileProductRepository(String fileName) {
        super(fileName);
        loadFromFile();
    }

    @Override
    protected Integer getId(Product entity) {
        return entity.getId();
    }

    @Override
    protected Product extractEntity(String line) {

        String[] elems = line.split(",");

        int id = Integer.parseInt(elems[0]);
        String name = elems[1];
        double price = Double.parseDouble(elems[2]);
        CategorieBautura categorie = CategorieBautura.valueOf(elems[3]);
        TipBautura tip = TipBautura.valueOf(elems[4]);

        return new Product(id, name, price, categorie, tip);
    }

    @Override
    protected String createEntityAsString(Product entity) {
        return entity.getId() + "," +
                entity.getNume() + "," +
                entity.getPret() + "," +
                entity.getCategorie() + "," +
                entity.getTip();
    }
}