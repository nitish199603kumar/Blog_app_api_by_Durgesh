package in.nitish.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import in.nitish.blog.repository.UserRepo;

//@SpringBootTest
class ApplicationTests {
	
	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void repoTest()
	{
		String className = this.userRepo.getClass().getName();
		Package packageName = this.userRepo.getClass().getPackage();
		System.out.println(className);
		System.out.println(packageName);
		
		
	}

}
