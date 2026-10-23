package ru.urabov.lab8.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Данные формы регистрации и строки списка пользователей.
 * Сущность User в форму не передаётся: в ней хранится хеш пароля и роли,
 * которые пользователь не должен задавать сам.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Укажите имя")
    private String firstName;

    @NotBlank(message = "Укажите фамилию")
    private String lastName;

    @NotBlank(message = "Укажите email")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "Укажите пароль")
    @Size(min = 6, message = "Пароль не короче 6 символов")
    private String password;

    // Роли через запятую, только для вывода в списке пользователей
    private String roles;
}
