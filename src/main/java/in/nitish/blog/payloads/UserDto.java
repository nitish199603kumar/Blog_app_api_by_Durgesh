package in.nitish.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import in.nitish.blog.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserDto {

	private Integer id;
	
	@NotEmpty
	@Size(min=4,message="Username must be min of 4 character")
	private String name;
	
	@Email(message="Email address is not valid !!")
	private String email;
	
//	@NotNull   //NotNull only stop null value,but not empty value
	@NotEmpty
	@Size(min=3,max=10, message="Password must be min 3 Char and max 10 char !!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}
