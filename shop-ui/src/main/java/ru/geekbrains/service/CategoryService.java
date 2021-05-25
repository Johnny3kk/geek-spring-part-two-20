package ru.geekbrains.service;

import ru.geekbrains.repr.CategoryRepr;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryRepr> findAll();

    CategoryRepr findById(Long id);

}
