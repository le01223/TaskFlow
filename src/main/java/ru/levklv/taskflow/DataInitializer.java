package ru.levklv.taskflow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.levklv.taskflow.entity.Task;
import ru.levklv.taskflow.entity.TaskPriority;
import ru.levklv.taskflow.entity.TaskStatus;
import ru.levklv.taskflow.repository.TaskRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public DataInitializer(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Task task = new Task();
        task.setTitle("First Task");
        task.setDescription("Check DB connection");
        task.setTaskStatus(TaskStatus.TODO);
        task.setTaskPriority(TaskPriority.MEDIUM);

        taskRepository.save(task);

        System.out.println("Task added to DB!");

        Task task2 = new Task();
        task2.setTitle("Second Task");
        task2.setDescription("Test user relations");
        task2.setTaskStatus(TaskStatus.IN_PROGRESS);
        task2.setTaskPriority(TaskPriority.HIGH);
        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Third Task");
        task3.setDescription("Another test task");
        task3.setTaskStatus(TaskStatus.DONE);
        task3.setTaskPriority(TaskPriority.LOW);
        taskRepository.save(task3);
    }
}
