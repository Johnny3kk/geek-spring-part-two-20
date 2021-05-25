package ru.geekbrains.service;

import ru.geekbrains.repr.CategoryRepr;

import java.util.List;

public interface CategoryService {

    List<CategoryRepr> findAll();

    CategoryRepr findById(Long id);

}
