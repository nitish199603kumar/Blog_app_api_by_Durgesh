package in.nitish.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.nitish.blog.config.AppConstants;
import in.nitish.blog.payloads.PostDto;
import in.nitish.blog.payloads.PostResponse;
import in.nitish.blog.service.FileService;
import in.nitish.blog.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path; 

	//Create Post
	
	@PostMapping("/user/{userId}/category/{categoryId}/createpost")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		System.out.println("Created Post " +createPost);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//Get Posts By Category Id
	//First Check specific category id in post table that category id is present or not
	@GetMapping("/getPostByCategoryid/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(categoryId);
		System.out.println("Post By Category Id" +postsByCategory);
		return new ResponseEntity<List<PostDto>>(postsByCategory,HttpStatus.OK);
		
	}
	
	//Get Posts By User Id
	//First Check specific User id in post table that user id is present or not
	@GetMapping("/getpostbyuserid/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId);
		System.out.println("Post By User Id" +postsByUser);
		return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
		
	}
	
	//Get all Posts
	
	@GetMapping("/getallposts")
//	public ResponseEntity<List<PostDto>> getAllPosts(
			public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
			)
	{
		 PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		System.out.println("Get all Post Response" +postResponse);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	//Get one post
	@GetMapping("/getpostbyid/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto onePost = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(onePost,HttpStatus.OK);
		
	}
	
	//Delete Post
	
	@DeleteMapping("/deletepost/{postId}")
	public String deletePost(@PathVariable Integer postId)
	{
		String deletedPost = this.postService.deletePost(postId);
		return deletedPost;
		
	}
	
	//Update Post
	@PutMapping("/updatepost/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		PostDto updatepost = this.postService.updatepost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK);
		
	}
	
	//Search Post
	
	@GetMapping("/searchpostbytitle/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword)
	{
		List<PostDto> searchPosts = this.postService.searchPosts(keyword);
		
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	}
	
	//Upload/Update Image/File in Post
	@PostMapping("/post/uploadimage/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
	
			) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String uploadedFileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(uploadedFileName);
		PostDto updatepost = this.postService.updatepost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK);
		
		
	}
	
	//Method to serve file
		@GetMapping(value="post/getimage/{imageName}", produces=MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException
		{
			InputStream resource = this.fileService.getResource(path, imageName);
			response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		}
	
}
