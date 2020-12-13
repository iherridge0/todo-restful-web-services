package za.co.iherridge0.rest.webservices.todorestfulwebservices;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.jwt.JwtJPAUserDetailsService;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.entities.User;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.exceptions.UserNotFoundException;
import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.resources.UserResource;

@SpringBootTest
class NewUserLoginTest {

	@Autowired
	UserResource userResource;

	@Autowired
	JwtJPAUserDetailsService jwtJPAUserDetailsService;

	@Test
	void userNotFound() {
		try {
			EntityModel<Optional<User>> user = userResource.retrieveJPAUser("test3");
			assertTrue(user == null);
		} catch (Exception e) {
			assertTrue(e instanceof UserNotFoundException);
		}
	}

	@Test
	void newUsers() {
		userResource.createJPAUser(new User("test1", "test1"));
		assertEquals("test1", userResource.retrieveJPAUser("test1").getContent().get().getUsername());
	}

	@Test
	void loginSuccessful() {
		userResource.createJPAUser(new User("test2", "test2"));
		EntityModel<Optional<User>> user = userResource.retrieveJPAUser("test2");
		UserDetails userDetails = jwtJPAUserDetailsService.loadUserByUsername(user.getContent().get().getUsername());
		assertFalse(userDetails.getAuthorities().isEmpty());

	}

	@Test
	void loginUnuccessful() {
		try {
			jwtJPAUserDetailsService.loadUserByUsername("test3");
		} catch (Exception e) {
			assertTrue(e instanceof UsernameNotFoundException);
		}

	}

}
