package com.example.todo_list.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository repository;


    @PostMapping
    public ResponseEntity<TaskCreateResponse> createTask(@RequestBody TaskCreateRequestPayload payload) {
        Task newTask = new Task(payload);

        this.repository.save(newTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TaskCreateResponse(newTask.getId()));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = this.repository.findAll();

        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable UUID taskId) {
        Optional<Task> task = this.repository.findById(taskId);

        if (task.isPresent()) {
            Task rawTask = task.get();
            return ResponseEntity.ok(rawTask);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<Task> updateIsCompleted(@PathVariable UUID taskId) {
        Optional<Task> task = this.repository.findById(taskId);

        if (task.isPresent()) {
            Task rawTask = task.get();

            rawTask.setIsCompleted(true);

            this.repository.save(rawTask);

            return ResponseEntity.ok(rawTask);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTaskDetails(@PathVariable UUID taskId, @RequestBody TaskUpdateRequestPayload payload) {
        Optional<Task> task = this.repository.findById(taskId);

        if (task.isPresent()) {
            Task rawTask = task.get();
            rawTask.setTitle(payload.title());
            rawTask.setIsCompleted(payload.is_completed());
            rawTask.setUpdatedAt(LocalDateTime.now());

            this.repository.save(rawTask);

            return ResponseEntity.ok(rawTask);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID taskId) {
        Optional<Task> task = this.repository.findById(taskId);

        if (task.isPresent()) {
            this.repository.deleteById(taskId);

            return ResponseEntity.ok("Task was deleted.");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAllTasks() {
        List<Task> tasks = this.repository.findAll();

        if (!tasks.isEmpty()) {
            this.repository.deleteAll();

            return ResponseEntity.ok("All tasks were deleted.");
        }

        return ResponseEntity.noContent().build();
    }
}
