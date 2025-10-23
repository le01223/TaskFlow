package ru.levklv.taskflow.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column()
    private String role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
                                // orphanRemoval используется для удаления связанных задач
                                // при удалении пользователя
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
