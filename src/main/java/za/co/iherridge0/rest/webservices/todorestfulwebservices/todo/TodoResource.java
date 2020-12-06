package za.co.iherridge0.rest.webservices.todorestfulwebservices.todo;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.URIEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public List<Todo> getAllTodos(@PathVariable String username) {
		return todoService.findAll();
	}
	
	/**
	 * Retrieve a todo for a user
	 * @param username
	 * @param id
	 * @return  {@code Todo} If found
	 * 			{@code null} if not found
	 */
	@GetMapping(path = "/users/{username}/todos/{id}")
	public ResponseEntity<Todo> getTodo(@PathVariable String username, @PathVariable long id) {
		Optional<Todo> todo = todoService.findById(id);
		if(!todo.isEmpty())
			return ResponseEntity.ok(todo.get());
		else
			return ResponseEntity.notFound().build();
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
		try {
			todoService.deleteById(id);	
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	// Edit/Update a Todo
	// PUT /users/{username}/todos/{todoid}
	@PutMapping("users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		
		Todo todoUpdated = todoService.save(todo);
				
		return new ResponseEntity<Todo>(todoUpdated, HttpStatus.OK);
	}

	// Create a new Todo
	// POST /users/{username}/todos/
	@PostMapping("/users/{username}/todos")
	public ResponseEntity<Void> createTodo(@RequestBody Todo todo, @PathVariable String username) {
		Todo savedTodo = todoService.save(new Todo(username, todo.getDescription(), new Date(), false));
		
		//Location
		//Get current resource
		//{id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(savedTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();

	}

}
