package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
