package in.nitish.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.nitish.blog.entity.User;
import in.nitish.blog.exception.ResourceNotFoundException;
import in.nitish.blog.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "email"+username, 0));
		System.out.println("(CustomUserDetailService)Get email as username from user Repository --->" +user);
		System.out.println("(CustomUserDetailService)Get Password from user Repository --->" +user.getPassword());
		System.out.println("(CustomUserDetailService)Get email as username from user Repository --->" +user.getEmail());
		return user;
	}

}
