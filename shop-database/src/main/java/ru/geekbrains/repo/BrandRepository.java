package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findBrandByName(String name);

}
