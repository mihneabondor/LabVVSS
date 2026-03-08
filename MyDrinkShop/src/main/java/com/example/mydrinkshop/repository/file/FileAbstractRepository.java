package com.example.mydrinkshop.repository.file;

import com.example.mydrinkshop.repository.AbstractRepository;

import java.io.*;

public abstract class FileAbstractRepository<ID, E>
        extends AbstractRepository<ID, E> {

    protected String fileName;

    protected FileAbstractRepository(String fileName) {
        this.fileName = fileName;
        //loadFromFile();
    }

    protected void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                E entity = extractEntity(line);
                super.save(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (E entity : entities.values()) {
                bw.write(createEntityAsString(entity));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E save(E entity) {
        E e = super.save(entity);
        writeToFile();
        return e;
    }

    @Override
    public E delete(ID id) {
        E e = super.delete(id);
        writeToFile();
        return e;
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        writeToFile();
        return e;
    }

    protected abstract E extractEntity(String line);

    protected abstract String createEntityAsString(E entity);
}
