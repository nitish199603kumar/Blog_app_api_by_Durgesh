package in.nitish.blog.service.impl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.nitish.blog.entity.Category;
import in.nitish.blog.entity.Post;
import in.nitish.blog.entity.User;
import in.nitish.blog.exception.ResourceNotFoundException;
import in.nitish.blog.payloads.PostDto;
import in.nitish.blog.payloads.PostResponse;
import in.nitish.blog.repository.CategoryRepo;
import in.nitish.blog.repository.PostRepo;
import in.nitish.blog.repository.UserRepo;
import in.nitish.blog.service.PostService;


@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatepost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public String deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		this.postRepo.delete(post);
		return "Post is deleted";
		
	}

	//Pagesize start with=0
	//pageNumber
	//pageSize
	//totalElement
	//totalPages
	//lastPage
	//content
	@Override
//	public List<PostDto> getAllPost(Integer pageNumber,Integer pageSize) {
	
	
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
//		int pageNumber=5;
//		int pageSize=1;
		
		//Using ternry operator
		
	//	Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}else {
			sort=Sort.by(sortBy).descending();
		}
		
		
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
	//	Pageable p=PageRequest.of(pageNumber, pageSize, Sort.by(sortBy))
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		
	//	List<Post> allPosts = this.postRepo.findAll();
		System.out.println("get all post from db :- " + allPosts.get(0));
		List<PostDto> allPostDtos = allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		System.out.println("get all postDtos from db :- "+allPostDtos.toString());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(allPostDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post postById = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		
		return this.modelMapper.map(postById, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id" , categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> findByTitleContaining = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = findByTitleContaining.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
