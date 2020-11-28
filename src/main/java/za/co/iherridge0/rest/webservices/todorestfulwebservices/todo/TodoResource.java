package za.co.iherridge0.rest.webservices.todorestfulwebservices.todo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.todo.entity.Todo;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.todo.repository.ToDoRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoResource {

	@Autowired
	private ToDoRepository todoService;

	/**
	 * Retrieve all Todos for a User 
	 * GET /users/{user_name}/todos
	 * 
	 * @param username
	 * @return List of Todos
	 */
	//
	@GetMapping(path = "/users/{username}/todos")
	public List<Todo> retrieveAllTodos(@PathVariable String username) {
		return todoService.findAll();
	}

	// 
	// 
	/**
	 * Delete a Todo of a User
	 * 
	 * DELETE /users/{username}/todo/{todoid}
	 * @param username
	 * @param id
	 * @return Not Found
	 */
	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {
		todoService.deleteById(id);		
		return ResponseEntity.noContent().build();
	}

	// Edit/Update a Todo
	// PUT /users/{username}/todos/{todoid}

	// Create a new Todo
	// POST /users/{username}/todos/
	@PostMapping("/users/{username}/todos")
	public Todo createTodo(@RequestBody Todo todo, @PathVariable String username) {
		Todo savedTodo = todoService.save(new Todo(username, todo.getDescription(), new Date(), false));
		return savedTodo;

	}

}
