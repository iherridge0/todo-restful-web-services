package za.co.iherridge0.rest.webservices.todorestfulwebservices.user.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.entities.User;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.exceptions.UserAlreadyExistException;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.exceptions.UserNotFoundException;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.repositories.UserRepository;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.service.UserDaoService;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;

	@Autowired
	private UserRepository userRepository;

	// GET /users
	// retrieveAllUsers
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	// GET /users
	// retrieveAllUsers
	@GetMapping("/jpa/users")
	public List<User> retrieveAllJPAUsers() {
		return userRepository.findAll();
	}

	// GET /users/{id}
	@GetMapping("/users/{username}")
	public EntityModel<User> retrieveUser(@PathVariable String username) {
		User user = service.findOne(username);

		if (user == null)
			throw new UserNotFoundException("username: " + username);

		// "all-users", SERVER_PATH + "/users"
		// retrieveAllUsers
		EntityModel<User> resource = EntityModel.of(user);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resource.add(linkTo.withRel("all-users"));
		return resource;
	}

	// GET /users/{id}
	@GetMapping("/jpa/users/{username}")
	public EntityModel<Optional<User>> retrieveJPAUser(@PathVariable String username) {
		Optional<User> user = userRepository.findById(username);

		if (!user.isPresent())
			throw new UserNotFoundException("username: " + username);

		// "all-users", SERVER_PATH + "/users"
		// retrieveAllUsers
		EntityModel<Optional<User>> resource = EntityModel.of(user);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resource.add(linkTo.withRel("all-users"));
		return resource;
	}

	// input - details of user
	// output - CREATED & Return the created URI
	// POST /users
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);

		// Need to return a 201 CREATED status with URI location of new created resource

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getUsername()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	// input - details of user
	// output - CREATED & Return the created URI
	// POST /users
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createJPAUser(@Valid @RequestBody User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedString = encoder.encode(user.getPassword());
			System.out.println(encodedString);
			user.setPassword(encodedString);
			
		Optional<User> findById = userRepository.findById(user.getUsername());
			
		if(findById.isPresent())
			throw new UserAlreadyExistException("Username (" + user.getUsername() + ") has already neem taken, please try again with a different username.");
			
		User savedUser = userRepository.save(user);

		// Need to return a 201 CREATED status with URI location of new created resource

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getUsername()).toUri();

		return ResponseEntity.created(location).build();
	}

	// DELETE /users/{id}
	@DeleteMapping("/users/{username}")
	public void deleteUser(@PathVariable String username) {

		User user = service.deleteById(username);

		if (user == null) {
			throw new UserNotFoundException("id-" + username);
		}
	}

}
