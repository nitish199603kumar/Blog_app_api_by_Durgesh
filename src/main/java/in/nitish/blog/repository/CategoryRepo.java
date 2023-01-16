package in.nitish.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nitish.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
