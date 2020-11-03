package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByName(String name);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
