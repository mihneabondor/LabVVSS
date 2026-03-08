package com.example.mydrinkshop.service.validator;

public interface Validator<T> {
    void validate(T entity);
}
