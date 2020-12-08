package za.co.iherridge0.rest.webservices.todorestfulwebservices.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.todo.entity.Todo;

@Repository
public interface ToDoRepository extends JpaRepository<Todo, Long> {
	
}
