package in.nitish.blog.service;

import java.util.List;

import in.nitish.blog.payloads.CategoryDto;

public interface CategoryService {

	 CategoryDto createCatogry(CategoryDto categoryDto);
	
	 CategoryDto updateCatogry(CategoryDto categoryDto,Integer categoryId);
	 
	 public void deleteCatogry(Integer categoryId);
	 
	 List<CategoryDto> getAllCatogry();
	 
	 CategoryDto getCatogryById(Integer categoryId);
	
	
}
