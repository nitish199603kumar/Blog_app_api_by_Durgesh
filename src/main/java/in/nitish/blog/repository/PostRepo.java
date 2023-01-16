package in.nitish.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nitish.blog.entity.Category;
import in.nitish.blog.entity.Post;
import in.nitish.blog.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByCategory(Category category);
	List<Post> findByUser(User user);
	
	List<Post> findByTitleContaining(String title);

}
