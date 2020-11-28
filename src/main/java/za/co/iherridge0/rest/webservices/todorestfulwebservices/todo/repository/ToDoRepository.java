package za.co.iherridge0.rest.webservices.todorestfulwebservices.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.todo.entity.Todo;

public interface ToDoRepository extends JpaRepository<Todo, Integer> {
	
}
