package ru.levklv.taskflow.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.levklv.taskflow.entity.User;
import ru.levklv.taskflow.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute User user,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            return "auth/registration";
        }

        try {
            user.setRole("USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User savedUser = userService.createUser(user);

            if (savedUser != null) {
                return "redirect:/auth/login?success";
            } else {
                model.addAttribute("error", "Ошибка при регистрации");
                return "registration";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при регистрации: " + e.getMessage());
            return "registration";
        }
    }
}