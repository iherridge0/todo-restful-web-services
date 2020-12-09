package za.co.iherridge0.rest.webservices.todorestfulwebservices.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.entities.User;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<User>();
	
	static {
		users.add(new User("in28minutes", new Date(), "in28minutes@gmail.com"));
		users.add(new User("eve", new Date(), "eve@gmail.com"));
		users.add(new User("jack", new Date(), "jack@gmail.com"));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		users.add(user);
		return user;
	}
	
	public User findOne(String username) {
		for(User user:users) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(String username) {
		for(int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if(user.getUsername() == username) {
				users.remove(i);
				return user;
			}
		}
		return null;
	}

}
