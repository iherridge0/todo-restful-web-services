package za.co.iherridge0.rest.webservices.todorestfulwebservices.todo;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.entities.User;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.exceptions.UserNotFoundException;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.repositories.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoResource {

	@Autowired
	private ToDoRepository todoRepository;
	
	@Autowired
	private UserRepository userRepository;

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
		Optional<User> userOptional = userRepository.findById(username);
		
		if(!userOptional.isPresent()) 
			throw new UserNotFoundException("username: " + username + " does not exist");
		
		return userOptional.get().getTodos();
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
		Optional<Todo> todo = todoRepository.findById(id);
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
			todoRepository.deleteById(id);	
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	// Edit/Update a Todo
	// PUT /users/{username}/todos/{todoid}
	@PutMapping("users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		Optional<User> firstUser = userRepository.findById(username);
		
		if(!firstUser.isPresent()) {
			throw new UserNotFoundException("username: " + username + " does not exist");
		}
		
		User user = firstUser.get();
		todo.setUser(user);
		
		Todo todoUpdated = todoRepository.save(todo);
				
		return new ResponseEntity<Todo>(todoUpdated, HttpStatus.OK);
	}

	// Create a new Todo
	// POST /users/{username}/todos/
	@PostMapping("/users/{username}/todos")
	public ResponseEntity<Void> createTodo(@RequestBody Todo todo, @PathVariable String username) {
		Optional<User> firstUser = userRepository.findById(username);
		
		if(!firstUser.isPresent()) {
			throw new UserNotFoundException("username: " + username + " does not exist");
		}
		
		User user = firstUser.get();
		todo.setUser(user);
		Todo savedTodo = todoRepository.save(todo);
		
		//Location
		//Get current resource
		//{id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(savedTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();

	}

}
