package in.nitish.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nitish.blog.entity.Comment;

public interface CommentsRepo extends JpaRepository<Comment, Integer> {

}
