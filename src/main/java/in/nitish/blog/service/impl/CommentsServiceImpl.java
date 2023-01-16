package in.nitish.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nitish.blog.entity.Comment;
import in.nitish.blog.entity.Post;
import in.nitish.blog.exception.ResourceNotFoundException;
import in.nitish.blog.payloads.CommentDto;
import in.nitish.blog.repository.CommentsRepo;
import in.nitish.blog.repository.PostRepo;
import in.nitish.blog.service.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentsRepo commentsRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = this.commentsRepo.save(comment);
		
		CommentDto commentDtos = this.modelMapper.map(savedComment, CommentDto.class);
		
		return commentDtos;
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentsRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Comment", commentId));
		this.commentsRepo.delete(comment);
		
	}

}
