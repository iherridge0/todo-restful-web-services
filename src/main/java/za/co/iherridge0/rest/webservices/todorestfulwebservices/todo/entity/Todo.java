package za.co.iherridge0.rest.webservices.todorestfulwebservices.todo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.entities.User;

@Entity
@Table(name = "Todo")
public class Todo {

	@Id
	@GeneratedValue
	private long id;
	
	private String description;
	
	//@JsonFormat(pattern="yyyy-MM-dd")
	private Date targetDate;
	
	private boolean isDone;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	protected Todo() {
		
	}

	public Todo(String description, Date targetDate) {
		super();
		this.description = description;
		this.targetDate = targetDate;
		this.isDone = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
