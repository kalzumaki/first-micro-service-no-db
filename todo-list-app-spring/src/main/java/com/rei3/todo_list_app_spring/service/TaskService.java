package com.rei3.todo_list_app_spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rei3.todo_list_app_spring.model.Task;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final AtomicInteger idCounter = new AtomicInteger();

    public boolean doesUserExistInLaravel (Integer userID) {
        // Simulate a check in the Laravel database
       String checkUrl = "http://laravel-user-services:8000/users/" + userID;

       try {
          ResponseEntity<String> resp = restTemplate.getForEntity(checkUrl, String.class);
           return resp.getStatusCode() == HttpStatus.OK;
       } catch (Exception e) {
           // If there's an error (e.g., user not found), return false
           return false;
       }
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Optional<Task> getTaskById(Integer id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public Optional<Task> createTask(Integer userID, String desc){
        if (!doesUserExistInLaravel(userID)) {
            System.out.println("User with ID " + userID + " does not exist");
            throw new IllegalArgumentException("User with ID " + userID + " does not exist");
        }
        System.out.println("Creating task with ID " + userID);
        Task newTask = new Task();
        newTask.setId(idCounter.incrementAndGet());
        newTask.setTitle("Task Of " + userID);
        newTask.setDescription(desc);
        newTask.setUserId(userID);
        
        tasks.add(newTask);

        return Optional.of(newTask);
    }
}

