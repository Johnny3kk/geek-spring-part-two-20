package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.repr.ProductRepr;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    List<ProductRepr> findAll();

    ProductRepr findById(Long id);

    List<ProductRepr> findByCategoryId(Long id);

}
