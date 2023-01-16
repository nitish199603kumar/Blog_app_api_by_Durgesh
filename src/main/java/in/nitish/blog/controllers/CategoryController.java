package in.nitish.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nitish.blog.payloads.ApiResponse;
import in.nitish.blog.payloads.CategoryDto;
import in.nitish.blog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

//	create Category

	@PostMapping("/createcategory")
	public ResponseEntity<CategoryDto> createCatogry(@Valid @RequestBody CategoryDto categoryDto) {
		
		
		CategoryDto categoryDtos=this.categoryService.createCatogry(categoryDto);
		System.out.println("Add category Response" +categoryDtos);
		return new ResponseEntity<CategoryDto>(categoryDtos,HttpStatus.CREATED);
	}
	
//Update Category
		@PutMapping("/updatecategory/{catid}")
	 public ResponseEntity<CategoryDto> updateCatogry(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("catid") Integer categoryId)
	 {
		 CategoryDto updateCatogry = this.categoryService.updateCatogry(categoryDto, categoryId);
		 
		return new ResponseEntity<>(updateCatogry,HttpStatus.OK);
		 
	 }
	 
// Delete Category
	@DeleteMapping("deletecategory/{catid}")
	public ResponseEntity<ApiResponse> deleteCatogry(@PathVariable("catid") Integer categoryId)
	{
		this.categoryService.deleteCatogry(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted SuccessFully",true),HttpStatus.OK);
	}
//GetAllCategory
	@GetMapping("/getallcategory")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		List<CategoryDto> allCatogry = this.categoryService.getAllCatogry();
		
		return new ResponseEntity<List<CategoryDto>>(allCatogry,HttpStatus.OK);
		
	}

//Get One Category Information
	@GetMapping("/getcategorybyid/{catid}")
	public ResponseEntity<CategoryDto> getCatogryById(@PathVariable("catid") Integer categoryId)
	{
		CategoryDto catogryByIdValue = this.categoryService.getCatogryById(categoryId);
		
		return new ResponseEntity<CategoryDto>(catogryByIdValue,HttpStatus.OK);
		
	}
}
