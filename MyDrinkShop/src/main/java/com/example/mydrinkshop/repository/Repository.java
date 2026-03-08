package com.example.mydrinkshop.repository;

import java.util.List;

public interface Repository<ID, E> {

    E findOne(ID id);

    List<E> findAll();

    E save(E entity);

    E delete(ID id);

    E update(E entity);
}
