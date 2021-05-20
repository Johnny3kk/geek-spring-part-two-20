package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
