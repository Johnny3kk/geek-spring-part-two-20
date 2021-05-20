package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
