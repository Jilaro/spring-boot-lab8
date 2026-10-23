package ru.urabov.lab8.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.urabov.lab8.dto.UserDto;
import ru.urabov.lab8.entity.Role;
import ru.urabov.lab8.entity.User;
import ru.urabov.lab8.repository.RoleRepository;
import ru.urabov.lab8.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName().trim() + " " + userDto.getLastName().trim());
        user.setEmail(userDto.getEmail().trim().toLowerCase());
        // В базу записывается только хеш BCrypt
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Первый зарегистрированный пользователь – администратор, остальные – обычные пользователи
        String roleName = userRepository.count() == 0 ? ROLE_ADMIN : ROLE_USER;
        user.setRoles(List.of(findOrCreateRole(roleName)));

        userRepository.save(user);
        log.info("Зарегистрирован пользователь {} с ролью {}", user.getEmail(), roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserDto)
                .toList();
    }

    private UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        // Имя и фамилия хранятся одной строкой, делим по первому пробелу
        String[] parts = user.getName().split(" ", 2);
        dto.setFirstName(parts[0]);
        dto.setLastName(parts.length > 1 ? parts[1] : "");
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", ")));
        return dto;
    }

    // Роль создаётся при первом обращении, поэтому заполнять таблицу ROLES вручную не нужно
    private Role findOrCreateRole(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(new Role(name)));
    }
}
