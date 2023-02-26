package in.nitish.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import in.nitish.blog.controllers.UserController;
import in.nitish.blog.payloads.RoleDto;
import in.nitish.blog.payloads.UserDto;
import in.nitish.blog.repository.UserRepo;
import in.nitish.blog.service.impl.UserServiceImpl;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes=ApplicationTests.class)
class ApplicationTests {
	
//	@Mock
//	private UserRepo userRepo;

	
	
	@Test
	void contextLoads() {
		
		assertTrue(true);
	}


		
	
	
//	@Test
//	public void repoTest()
//	{
//		String className = this.userRepo.getClass().getName();
//		Package packageName = this.userRepo.getClass().getPackage();
//		System.out.println(className);
//		System.out.println(packageName);
//		
//		
//	}

}
