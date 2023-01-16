package in.nitish.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.nitish.blog.config.AppConstants;
import in.nitish.blog.entity.Role;
import in.nitish.blog.entity.User;
import in.nitish.blog.exception.*;
import in.nitish.blog.payloads.UserDto;
import in.nitish.blog.repository.RoleRepo;
import in.nitish.blog.repository.UserRepo;
import in.nitish.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger Logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto user) {
		
		User dtoToUser = this.dtoToUser(user);
		System.out.println("Created User From Dto :-" +dtoToUser.getName());
		System.out.println("Created User From Dto :-" +dtoToUser.toString());
		
		User savedUser = this.userRepo.save(dtoToUser);
		System.out.println("Saved User In db" +savedUser.getId());
		System.out.println("Saved User In db" +savedUser.toString());
		
		return this.UserToDto(savedUser);
	}
	
	

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException(" User "," id ", userId));
		
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		UserDto updatedUserDto=this.UserToDto(updatedUser);
		
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		return this.UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		List<User> userList = this.userRepo.findAll();
		
		
		List<UserDto> userDtoList = userList.stream().map((user)->this.UserToDto(user)).collect(Collectors.toList());
		return userDtoList;
		
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		System.out.println("Get User for Delete"+user.toString());
//		 User userById = this.userRepo.findById();
//		 System.out.println("userById" +userById);
//		 if(userById.getId().equals(userId))
//		 {
//			 this.userRepo.delete(userById);
//			 System.out.println("User Deleted Successfully");
//		 }
		 
		 
		this.userRepo.delete(user);
	}
	
	public User dtoToUser(UserDto userDto)
	{
		
		User user=this.modelMapper.map(userDto, User.class);
//		User user =new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		return user;
	}
	
	public UserDto UserToDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		UserDto userDto= new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
	}



	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		 user.setPassword(this.passwordEncoder.encode(user.getPassword()));
	//	 Optional<Role> findById = this.roleRepo.findById(AppConstants.NORMAL_USER); //Optional handles Null bcz may be null comes from db 
		 
		 //roles
		 Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		 
		 user.getRoles().add(role);
		 User newUser = this.userRepo.save(user);
		 
		
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
