package in.nitish.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.nitish.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse=new ApiResponse();
		apiResponse.setMessage(message);
		apiResponse.setSuccess(false);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String,String> errorsResponse=new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			
			String errorFieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			
			System.out.println("errorFieldName" +errorFieldName);
			System.out.println("errorMessage" +errorMessage);
			
			errorsResponse.put(errorFieldName, errorMessage);
			
		});
		
		return new ResponseEntity<Map<String,String>>(errorsResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex)
	{
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
	
}
