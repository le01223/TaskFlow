package ru.levklv.taskflow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.levklv.taskflow.entity.Task;
import ru.levklv.taskflow.entity.TaskPriority;
import ru.levklv.taskflow.entity.TaskStatus;
import ru.levklv.taskflow.entity.User;
import ru.levklv.taskflow.security.CustomUserDetails;
import ru.levklv.taskflow.service.TaskService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getUserTasks(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        List<Task> tasks = taskService.findByUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @PostMapping
    public String createTask(@ModelAttribute Task task,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        task.setUser(user);

        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/create";
    }

    @GetMapping("/{id}")
    public String showTask(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        Task task = taskService.findById(id);

        if (task == null || !task.getUser().getId().equals(user.getId())) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);
        return "tasks/show";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Task task = taskService.findById(id);

        if (task != null && task.getUser().getId().equals(user.getId())) {
            taskService.deleteTask(id);
        }

        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model) {
        User user = userDetails.getUser();
        Task task = taskService.findById(id);

        if (task == null || !task.getUser().getId().equals(user.getId())) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateTask(@PathVariable int id,
                             @ModelAttribute Task updatedTask,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Task existingTask = taskService.findById(id);

        if (existingTask == null || !existingTask.getUser().getId().equals(user.getId())) {
            return "redirect:/tasks";
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDeadline(updatedTask.getDeadline());
        existingTask.setTaskStatus(updatedTask.getTaskStatus());
        existingTask.setTaskPriority(updatedTask.getTaskPriority());

        taskService.updateTask(existingTask);
        return "redirect:/tasks/" + id;
    }
}