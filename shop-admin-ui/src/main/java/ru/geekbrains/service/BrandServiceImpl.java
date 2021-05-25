package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repr.BrandRepr;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);


    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandRepr> findAll() {
        return brandRepository.findAll().stream().map(BrandRepr::new).collect(Collectors.toList());
    }

    @Override
    public BrandRepr findById(Long id) {
        return new BrandRepr(brandRepository.findById(id).orElseThrow(() -> new NotFoundException())) ;
    }

}
