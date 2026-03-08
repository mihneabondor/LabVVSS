package com.example.mydrinkshop.service;

import com.example.mydrinkshop.domain.Reteta;
import com.example.mydrinkshop.repository.Repository;

import java.util.List;

public class RetetaService {

    private final Repository<Integer, Reteta> retetaRepo;

    public RetetaService(Repository<Integer, Reteta> retetaRepo) {
        this.retetaRepo = retetaRepo;
    }

    public void addReteta(Reteta r) {
        retetaRepo.save(r);
    }

    public void updateReteta(Reteta r) {
        retetaRepo.update(r);
    }

    public void deleteReteta(int id) {
        retetaRepo.delete(id);
    }

    public Reteta findById(int id) {
        return retetaRepo.findOne(id);
    }

    public List<Reteta> getAll() {
        return retetaRepo.findAll();
    }
}