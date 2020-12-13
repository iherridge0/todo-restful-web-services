package za.co.iherridge0.rest.webservices.todorestfulwebservices.jwt;

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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> findUser = userRepository.findById(username);

		if (!findUser.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return new JwtUserDetails(1L, username, findUser.get().getPassword(), "ROLE_USER_2");

	}

}
