package ru.urabov.lab8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urabov.lab8.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
