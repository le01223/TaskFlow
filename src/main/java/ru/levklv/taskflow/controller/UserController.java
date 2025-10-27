package ru.levklv.taskflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.levklv.taskflow.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("user", userService.findAll());
        return "user/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/changeRole/{id}")
    public String changeRole(@PathVariable Integer id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return "redirect:/admin";
    }
}