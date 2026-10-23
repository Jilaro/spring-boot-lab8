package ru.urabov.lab8.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.urabov.lab8.dto.UserDto;
import ru.urabov.lab8.service.UserService;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result) {
        if (userDto.getEmail() != null && userService.existsByEmail(userDto.getEmail())) {
            result.rejectValue("email", "duplicate",
                    "На этот адрес электронной почты уже зарегистрирована учетная запись");
        }
        if (result.hasErrors()) {
            return "register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }
}
