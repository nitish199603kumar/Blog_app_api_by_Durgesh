package in.nitish.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nitish.blog.entity.User;

public interface UserRepo extends JpaRepository<User,Integer> {

//	User findById();

	Optional<User> findByEmail(String email);
}
