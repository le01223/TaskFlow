package ru.levklv.taskflow.controller;

import org.springframework.stereotype.Controller;
import ru.levklv.taskflow.service.TaskService;

@Controller
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
}
