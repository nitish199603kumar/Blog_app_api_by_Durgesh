package in.nitish.blog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import in.nitish.blog.config.AppConstants;
import in.nitish.blog.entity.Role;
import in.nitish.blog.repository.RoleRepo;
import in.nitish.blog.repository.UserRepo;
import net.bytebuddy.asm.Advice.This;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//First encoded that password for particular email and paste into user table in password.
		
		System.out.println("(Application.main class)Encoded Password check -> " +this.passwordEncoder.encode("abcd"));
		System.out.println("For Using All api You have to create token as the basis of Role(ADMIN,USER),"
				+ "First you have to create encrypted password for user(email) in user table,"
				+ "and declare role in role table "
				+ "then u used login request and create token api for create token,"
				+ " Normal user can not delete any user");
		
		
		try {
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			
			List<Role> roles=new ArrayList<>();	
			roles.add(role);
			roles.add(role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
				
			});
						
		} catch (Exception e) {
			
			
		}
	}
	
	

}
