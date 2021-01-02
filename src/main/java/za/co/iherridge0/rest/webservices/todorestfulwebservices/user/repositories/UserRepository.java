package za.co.iherridge0.rest.webservices.todorestfulwebservices.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.iherridge0.rest.webservices.todorestfulwebservices.user.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	public Optional<User> findByUsername(String username);

}
