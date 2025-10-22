package ru.levklv.taskflow.controller;

import org.springframework.stereotype.Controller;
import ru.levklv.taskflow.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
