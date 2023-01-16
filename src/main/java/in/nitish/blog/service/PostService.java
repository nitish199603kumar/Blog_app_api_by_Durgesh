package in.nitish.blog.service;

import java.util.List;

import in.nitish.blog.entity.Post;
import in.nitish.blog.payloads.PostDto;
import in.nitish.blog.payloads.PostResponse;

public interface PostService {

	//create Post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update Post
	
	PostDto updatepost(PostDto postDto,Integer postId);
	
	//delete Post
	
	String deletePost(Integer postId);
	
	//Get All Post
	
//	List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//Get One Post
	PostDto getPostById(Integer postId);
	
	//Get All Posts by Category
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//Get All posts by User
	
	List<PostDto> getPostsByUser(Integer userId);
	
	//search Posts  
	List<PostDto> searchPosts(String keyword);
}
