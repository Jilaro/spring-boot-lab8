package ru.urabov.lab8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urabov.lab8.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
