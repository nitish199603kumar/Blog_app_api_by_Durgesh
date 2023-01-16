package in.nitish.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nitish.blog.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
