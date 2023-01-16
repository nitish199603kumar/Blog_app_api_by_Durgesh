package in.nitish.blog.controllers;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nitish.blog.exception.ApiException;
import in.nitish.blog.payloads.JwtAuthRequest;
import in.nitish.blog.payloads.JwtAuthResponse;
import in.nitish.blog.payloads.UserDto;
import in.nitish.blog.security.JwtTokenHelper;
import in.nitish.blog.service.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController{
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		this.authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		System.out.println("(Controller) UserDetails as username(email) " +userDetails);
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		System.out.println("TOKEN ->" +token);
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws Exception {
		
		System.out.println("Authenticate UserName -> " +username + " and -> " + "Authenticate Password -> " +password);
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			
			this.authenticationManager.authenticate(authenticationToken);
			System.out.println("Nitish Kumar");
		} catch (Exception e) {
			
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid username or password !!");
			
		}
		
	
		
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
	
	
}