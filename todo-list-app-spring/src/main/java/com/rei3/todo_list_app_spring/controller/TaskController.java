package com.rei3.todo_list_app_spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rei3.todo_list_app_spring.dto.CreateTask;
import com.rei3.todo_list_app_spring.model.Task;
import com.rei3.todo_list_app_spring.service.TaskService;



@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<?> helloWorld(){
        return ResponseEntity.ok("hello world I made changes");
    }

    @GetMapping("/all-task")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
                   
    }

    @PostMapping("/create-task")
    public ResponseEntity<?> createTask(@RequestBody CreateTask createTask) {
        try {
            Optional<Task> newTask = taskService.createTask(createTask.getUserId(), createTask.getDescription());
            return newTask.map(task -> ResponseEntity.ok(new CreateTask(task.getUserId(), task.getDescription())))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
