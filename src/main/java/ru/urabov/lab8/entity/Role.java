package ru.urabov.lab8.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Роль пользователя: ROLE_ADMIN или ROLE_USER. Таблица ROLES.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название роли с префиксом ROLE_, как принято в Spring Security
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Пользователи с этой ролью. Связь хранится в таблице users_roles,
     * её описание – в классе User (mappedBy = "roles")
     */
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }
}
