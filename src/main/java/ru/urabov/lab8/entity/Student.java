package ru.urabov.lab8.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность «Студент». Таблица STUDENTS.
 */
@Entity
@Table(name = "students")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Укажите имя")
    @Size(max = 15, message = "Не больше 15 символов")
    private String name;

    @NotBlank(message = "Укажите фамилию")
    @Size(max = 25, message = "Не больше 25 символов")
    private String surname;

    @Size(max = 20, message = "Не больше 20 символов")
    private String faculty;

    @NotNull(message = "Укажите возраст")
    @Min(value = 14, message = "Возраст от 14 до 100")
    @Max(value = 100, message = "Возраст от 14 до 100")
    private Integer age;
}
