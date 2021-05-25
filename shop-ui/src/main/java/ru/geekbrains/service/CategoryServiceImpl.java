package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.repr.CategoryRepr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryRepr> findAll() {
        return categoryRepository.findAll().stream().map(CategoryRepr::new).collect(Collectors.toList());
    }

    @Override
    public CategoryRepr findById(Long id) {
        return new CategoryRepr(categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException()));
    }
}
