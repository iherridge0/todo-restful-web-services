package za.co.iherridge0.rest.webservices.todorestfulwebservices.user.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.todo.entity.Todo;

import java.util.*;

@ApiModel(description = "This is the user api.")
@Entity
public class User {

	@Id
	@Size(min=2, message = "Name should have atleast 2 characters.")
	@ApiModelProperty(notes = "The name should have atleast 2 characters.")
	private String username;
	
	private String password;
	
	@Past
	@ApiModelProperty(notes = "The birthDate must be in the past.")
	private Date birthDate;
	
	@Email
	@ApiModelProperty(notes = "Must be a valid email address.")
	private String email;
	
	@OneToMany(mappedBy = "user")
	private List<Todo> todos; 
	
	protected User() {
		
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, Date birthDate, String email) {
		super();
		this.username = username;
		this.birthDate = birthDate;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
}
