package in.nitish.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nitish.blog.payloads.ApiResponse;
import in.nitish.blog.payloads.UserDto;
import in.nitish.blog.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	//POST-Create user
	@PostMapping("/createuser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		logger.info("createuser request {} ", userDto.toString());
		UserDto createUser = userService.createUser(userDto);
		System.out.println("Response From Controller to create user :-"+createUser);
		return new ResponseEntity<>(createUser,HttpStatus.CREATED);
	}
	
	//GET-Get user
	
	//PUT-Update user
	@PutMapping("/updateuser/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId)
	{
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		System.out.println("Updated User From Controller" +updateUser);
		return new ResponseEntity<>(updateUser,HttpStatus.OK);
	}
	
	//DELETE-Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
	{
		this.userService.deleteUser(uid);
		return new ResponseEntity<>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
				
	}
	@GetMapping("/getalluser")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		List<UserDto> allUser = this.userService.getAllUser();
		return new ResponseEntity<>(allUser,HttpStatus.OK);
	}
	
	@GetMapping("/getoneuser/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId)
	{
		UserDto userById = this.userService.getUserById(userId);
		return new ResponseEntity<>(userById,HttpStatus.OK);
	}
	
}
