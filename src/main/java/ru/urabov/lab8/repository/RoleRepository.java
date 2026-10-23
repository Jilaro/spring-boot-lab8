package ru.urabov.lab8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urabov.lab8.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Запрос select ... where name = ? Spring Data строит по имени метода
    Optional<Role> findByName(String name);
}
