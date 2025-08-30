package com.rei3.todo_list_app_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTask {
    private Integer userId;
    private String description;

    // Getters and setters
}
