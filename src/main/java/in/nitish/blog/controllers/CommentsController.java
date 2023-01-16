package in.nitish.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nitish.blog.payloads.ApiResponse;
import in.nitish.blog.payloads.CommentDto;
import in.nitish.blog.service.CommentsService;

@RestController
@RequestMapping("/api")
public class CommentsController {

	@Autowired
	private CommentsService commentsService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId)
	{
		
		CommentDto createComment = this.commentsService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		
		this.commentsService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comments deleted Successfully !!",true),HttpStatus.OK) ;
		
	}

}
