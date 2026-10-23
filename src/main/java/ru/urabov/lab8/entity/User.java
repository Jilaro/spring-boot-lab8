package ru.urabov.lab8.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Пользователь приложения. Таблица USERS.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя и фамилия через пробел
     */
    @Column(nullable = false)
    private String name;

    /**
     * Email – он же логин, повторяться не может
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Пароль в виде хеша BCrypt, исходный пароль не хранится
     */
    @Column(nullable = false)
    private String password;

    /**
     * Роли пользователя. Связь «многие ко многим» через таблицу users_roles:
     * user_id – ссылка на пользователя, role_id – ссылка на роль.
     * EAGER: роли загружаются вместе с пользователем, они нужны при каждом входе
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();
}
