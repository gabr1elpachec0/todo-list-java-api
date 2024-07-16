package com.example.todo_list.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Task(TaskCreateRequestPayload payload) {
        this.title = payload.title();
        this.isCompleted = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
