package in.nitish.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	private String secret = "jwtTokenKey";
	
	//retrieve username from jwt token
	public String getUsernameFromToken(String token)
	{
		System.out.println("(JwtTokenHelper) Username from token  -->" +token);
		return getClaimFromToken(token,Claims::getSubject);
	}
	
	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token,Function<Claims, T> claimsResolver)
	{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	//For retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token)
	{
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//Check if the token has Expired
	private Boolean isTokenExpired(String token)
	{
		final Date expiration=getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//Generate token for user
	public String generateToken(UserDetails userDetails)
	{
		System.out.println("(JwtTokenHelper) Email as username " +userDetails.getUsername());
		System.out.println("(JwtTokenHelper) Password as password " +userDetails.getPassword());
		Map<String, Object> claims=new HashMap<>();
		return doGenerateToken(claims,userDetails.getUsername());
	}
	
	//while creating the token
	//1.Define claims of the token like Issuer,Expiration,Subject and the ID
	//2.Sign the JWT using the HS512 algorithm and secret key.
	//3.According to JWS compact serialization(https://tools.ietf.org/html/draft-left-josh)
	
	
	private String doGenerateToken(Map<String,Object> claims, String subject)
	{
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	//validate Token
	public boolean validateToken(String token,UserDetails userDetails)
	{
		final String username=getUsernameFromToken(token);
		System.out.println("(JwtTokenHelper)Get username from token " +username);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
}
