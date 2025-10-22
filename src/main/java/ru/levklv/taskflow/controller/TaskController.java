package ru.levklv.taskflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levklv.taskflow.entity.Task;
import ru.levklv.taskflow.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello from TaskFlow";
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.findById(id);
    }
}
