package ru.geekbrains.service;

import ru.geekbrains.repr.BrandRepr;

import java.util.List;

public interface BrandService {

    List<BrandRepr> findAll();

    BrandRepr findById(Long id);

}
