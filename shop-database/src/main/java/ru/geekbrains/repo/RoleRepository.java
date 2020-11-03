package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
