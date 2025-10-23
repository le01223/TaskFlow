package ru.levklv.taskflow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.levklv.taskflow.entity.Task;
import ru.levklv.taskflow.entity.TaskPriority;
import ru.levklv.taskflow.entity.TaskStatus;
import ru.levklv.taskflow.entity.User;
import ru.levklv.taskflow.security.CustomUserDetails;
import ru.levklv.taskflow.service.TaskService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getUserTasks(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        List<Task> tasks = taskService.findByUser(user);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Task task = taskService.findById(id);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        // Проверяем, принадлежит ли задача текущему пользователю
        if (!task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        task.setUser(user);

        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task taskDetails,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Task existingTask = taskService.findById(id);

        if (existingTask == null || !existingTask.getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        // Обновляем поля задачи
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setDeadline(taskDetails.getDeadline());
        existingTask.setTaskStatus(taskDetails.getTaskStatus());
        existingTask.setTaskPriority(taskDetails.getTaskPriority());

        Task updatedTask = taskService.updateTask(existingTask);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable int id, @RequestBody Map<String, TaskStatus> request,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Task existingTask = taskService.findById(id);

        if (existingTask == null || !existingTask.getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        TaskStatus newStatus = request.get("taskStatus");
        if (newStatus != null) {
            taskService.changeStatus(id, newStatus);
            Task updatedTask = taskService.findById(id);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<Task> updateTaskPriority(@PathVariable int id, @RequestBody Map<String, TaskPriority> request,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Task existingTask = taskService.findById(id);

        if (existingTask == null || !existingTask.getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        TaskPriority newPriority = request.get("taskPriority");
        if (newPriority != null) {
            taskService.changePriority(id, newPriority);
            Task updatedTask = taskService.findById(id);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable int id,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromDetails(userDetails);
        Task existingTask = taskService.findById(id);

        if (existingTask == null || !existingTask.getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        taskService.deleteTask(id);
        return ResponseEntity.ok(Map.of("message", "Задача успешно удалена"));
    }

    private User getUserFromDetails(CustomUserDetails userDetails) {
        // Получаем User из CustomUserDetails
        // В реальном приложении здесь может быть дополнительная логика
        return ((CustomUserDetails) userDetails).getUser();
    }
}