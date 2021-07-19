package ru.geekbrains.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testFindById() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName("Big way");

        Brand expectedBrand = new Brand();
        expectedBrand.setId(1L);
        expectedBrand.setName("Super Brand");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Big stuff");
        expectedProduct.setPrice(new BigDecimal(999));
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setBrand(expectedBrand);
        expectedProduct.setPictures(new ArrayList<>());

        when(productRepository.findById(eq(1L))).thenReturn(Optional.of(expectedProduct));

        ProductRepr opt = productService.findById(1L);

        assertEquals(expectedProduct.getId(), opt.getId());
        assertEquals(expectedProduct.getName(), opt.getName());
    }
}
