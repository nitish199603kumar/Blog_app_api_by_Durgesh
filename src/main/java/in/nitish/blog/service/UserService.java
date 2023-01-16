package in.nitish.blog.service;

import java.util.List;

import in.nitish.blog.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUser();
	void deleteUser(Integer userId);
}
