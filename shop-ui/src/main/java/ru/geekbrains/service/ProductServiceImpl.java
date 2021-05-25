package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repr.ProductRepr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream().map(ProductRepr::new).collect(Collectors.toList());
    }

    @Override
    public ProductRepr findById(Long id) {
        return new ProductRepr(productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException()));
    }

    @Override
    public List<ProductRepr> findByCategoryId(Long id) {
        return productRepository.findProductsByCategory_Id(id).stream().map(ProductRepr::new).collect(Collectors.toList());
    }
}
