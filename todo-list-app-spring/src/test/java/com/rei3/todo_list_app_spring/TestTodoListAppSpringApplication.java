package com.rei3.todo_list_app_spring;

import org.springframework.boot.SpringApplication;

public class TestTodoListAppSpringApplication {

	public static void main(String[] args) {
		SpringApplication.from(TodoListAppSpringApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
