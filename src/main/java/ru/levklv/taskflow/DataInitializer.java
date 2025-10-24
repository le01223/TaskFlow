package ru.levklv.taskflow;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.levklv.taskflow.entity.Task;
import ru.levklv.taskflow.entity.TaskPriority;
import ru.levklv.taskflow.entity.TaskStatus;
import ru.levklv.taskflow.entity.User;
import ru.levklv.taskflow.repository.TaskRepository;
import ru.levklv.taskflow.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(TaskRepository taskRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        User user2 = new User();
        user2.setEmail("lev@yandex.ru");
        user2.setPassword(passwordEncoder.encode("123"));
        user2.setRole("ROLE_ADMIN");
        userRepository.save(user2);

        Task task1 = new Task();
        task1.setTitle("First Task");
        task1.setDescription("Check DB connection");
        task1.setTaskStatus(TaskStatus.TODO);
        task1.setTaskPriority(TaskPriority.MEDIUM);
        task1.setUser(user);

        Task task2 = new Task();
        task2.setTitle("Second Task");
        task2.setDescription("Test user relations");
        task2.setTaskStatus(TaskStatus.IN_PROGRESS);
        task2.setTaskPriority(TaskPriority.HIGH);
        task2.setUser(user);

        Task task3 = new Task();
        task3.setTitle("Third Task");
        task3.setDescription("Another test task");
        task3.setTaskStatus(TaskStatus.DONE);
        task3.setTaskPriority(TaskPriority.LOW);
        task3.setUser(user);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
    }
}
