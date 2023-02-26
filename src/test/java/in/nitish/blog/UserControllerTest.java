package in.nitish.blog;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import in.nitish.blog.controllers.UserController;
import in.nitish.blog.payloads.RoleDto;
import in.nitish.blog.payloads.UserDto;
import in.nitish.blog.service.impl.UserServiceImpl;
import io.jsonwebtoken.lang.Arrays;

public class UserControllerTest {

	@Mock
	private UserServiceImpl userServiceImpl;

	@InjectMocks
	private UserController userController;

	
	private UserDto userDto;

	private RoleDto roleDto;


	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setUp() {

		System.out.println("SetUp");
		MockitoAnnotations.openMocks(this);
		
		roleDto=RoleDto.builder()
				.id(512)
				.name("Role_Normal")
				.build();
	
		userDto=UserDto.builder()
				.id(1)
				.email("nitish888kr@gmail.com")
				.password("12345")
				.about("This is nitish")
				.name("nitish")
				.build();
		
//		userDto.setId(1);
//		userDto.setAbout("This is Nitish");
//		userDto.setEmail("nitish888kr@gmail.com");
//		userDto.setName("Nitish");
//		userDto.setPassword("abcd");
//		userDto.setRoles((Set<RoleDto>) roleDto);

//		roleDto.setId(512);
//		roleDto.setName("Role_Admin");
		System.out.println("UserById  1" + userDto.getId());

	}

	@Test
	void getUserById_success() {

		System.out.println("UserById 2");

//		System.out.println("UserById" + userDto.getId());
		Mockito.when(userServiceImpl.getUserById(userDto.getId())).thenReturn(userDto);
		ResponseEntity<UserDto> userById = userController.getUserById(userDto.getId());
		
		System.out.println("Response" +userById);
		assertThat(userById).isNotNull();
		
	}
	
	@Test
	void createUser_success() {
		Mockito.when(userServiceImpl.createUser(userDto)).thenReturn(userDto);
		ResponseEntity<UserDto> createUser = userController.createUser(userDto);
		System.out.println("CreateUser : " +createUser);
		assertThat(createUser).isNotNull();
		assertThat(createUser.getBody().getEmail()).isEqualTo("nitish888kr@gmail.com");
		
	}
	

}
