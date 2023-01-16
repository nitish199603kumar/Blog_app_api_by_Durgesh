package in.nitish.blog.service;

import in.nitish.blog.payloads.CommentDto;

public interface CommentsService {

	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);
}
