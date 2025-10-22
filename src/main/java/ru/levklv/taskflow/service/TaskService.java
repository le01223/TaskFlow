package ru.levklv.taskflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.levklv.taskflow.entity.Task;
import ru.levklv.taskflow.entity.TaskPriority;
import ru.levklv.taskflow.entity.TaskStatus;
import ru.levklv.taskflow.entity.User;
import ru.levklv.taskflow.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task findById(int id) {
        Optional<Task> foundTask = taskRepository.findById(id);

        return foundTask.orElse(null);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    public void changeStatus(int id, TaskStatus taskStatus) {
        Task task = findById(id);
        task.setTaskStatus(taskStatus);
        taskRepository.save(task);
    }

    public void changePriority(int id, TaskPriority taskPriority) {
        Task task = findById(id);
        task.setTaskPriority(taskPriority);
        taskRepository.save(task);
    }
}
