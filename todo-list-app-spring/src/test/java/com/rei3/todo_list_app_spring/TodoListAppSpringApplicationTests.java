package com.rei3.todo_list_app_spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TodoListAppSpringApplicationTests {

	@Test
	void contextLoads() {
	}

}
