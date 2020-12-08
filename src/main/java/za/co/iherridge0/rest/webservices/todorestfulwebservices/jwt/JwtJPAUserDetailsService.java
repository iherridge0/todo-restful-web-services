package za.co.iherridge0.rest.webservices.todorestfulwebservices.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.entities.User;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.repositories.UserRepository;

@Service
public class JwtJPAUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

	static {
		inMemoryUserList.add(new JwtUserDetails(1L, "in28minutes",
				"$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e", "ROLE_USER_2"));
		inMemoryUserList.add(new JwtUserDetails(2L, "pieter",
				"$2a$10$KEKHrdvsCenVUWK2OClLO.r.4djq0kw5VUqrwaK./aOPU29NoxAWa", "ROLE_USER_2"));

		// $2a$10$IetbreuU5KihCkDB6/r1DOJO0VyU9lSiBcrMDT.biU7FOt2oqZDPm
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> findUser = userRepository.findById(username);
		
		if (!findUser.isPresent()) { throw new
			 UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username)); }
		
		return new JwtUserDetails(1L, username, findUser.get().getPassword(), "ROLE_USER_2");
		/*
		 * Optional<JwtUserDetails> findFirst = inMemoryUserList.stream() .filter(user
		 * -> user.getUsername().equals(username)).findFirst();
		 * 
		 * if (!findFirst.isPresent()) { throw new
		 * UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username)); }
		 * 
		 * return findFirst.get();
		 */
	}

}