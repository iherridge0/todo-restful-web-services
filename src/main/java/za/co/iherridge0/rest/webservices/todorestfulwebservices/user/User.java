package za.co.iherridge0.rest.webservices.todorestfulwebservices.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "This is the user api.")
@Entity
public class User {

	@Id
	@Size(min=2, message = "Name should have atleast 2 characters.")
	@ApiModelProperty(notes = "The name should have atleast 2 characters.")
	private String username;
	
	@Past
	@ApiModelProperty(notes = "The birthDate must be in the past.")
	private Date birthDate;
	
	@Email
	@ApiModelProperty(notes = "Must be a valid email address.")
	private String email;
	
	protected User() {
		
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
}
