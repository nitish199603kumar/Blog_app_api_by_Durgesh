package in.nitish.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nitish.blog.entity.Category;
import in.nitish.blog.exception.ResourceNotFoundException;
import in.nitish.blog.payloads.CategoryDto;
import in.nitish.blog.repository.CategoryRepo;
import in.nitish.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService
{
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCatogry(CategoryDto categoryDto) {
				
				Category category=this.dtoToCategory(categoryDto);
				
				Category savedCategory = this.categoryRepo.save(category);
				System.out.println("Saved Category " +savedCategory.toString());
			
		return this.categoryToDto(savedCategory);
	}

	@Override
	public CategoryDto updateCatogry(CategoryDto categoryDto, Integer categoryId) {
		   Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));	
		   
		   category.setCategoryTitle(categoryDto.getCategoryTitle());
		   category.setCategoryDescription(categoryDto.getCategoryDescription());
		   Category savedUpdatedCategory = this.categoryRepo.save(category);
		   System.out.println("Saved Updated Category :-" +savedUpdatedCategory.toString());
		   CategoryDto categoryToDto = this.categoryToDto(savedUpdatedCategory);
		   
		return categoryToDto ;
	}

	@Override
	public void deleteCatogry(Integer categoryId) {
		Category category  = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		System.out.println("findAllCategory" +category);
	
		this.categoryRepo.delete(category);
		
		
	}

//	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryDto> getAllCatogry() {
		
		List<Category> findAllCategory = this.categoryRepo.findAll();
		System.out.println("findAllCategory" +findAllCategory);
//		System.out.println("findAllCategory" +findAllCategory);
		List<CategoryDto> categoryToDtos = findAllCategory.stream().map((user)->this.categoryToDto(user)).collect(Collectors.toList());
		System.out.println("Category to Dtos" +categoryToDtos);
		return categoryToDtos;
//		List<CategoryDto> categoryDtos=null;
//		for(Category category:findAllCategory)
//		{
//		   
//			categoryDtos = this.categoryToDto(category);
//		}
//		
//		return categoryDtos;
		
//		List<CategoryDto> categoryToDto=null;
//		for(int i=0;i<findAllCategory.size();i++)
//		{
//			categoryToDto = (List<CategoryDto>) this.categoryToDto(findAllCategory.get(i));
//			
//		}
//		return categoryToDto;
	}

	@Override
	public CategoryDto getCatogryById(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		return this.categoryToDto(category);
	}
	
	
	
	public Category dtoToCategory(CategoryDto categoryDto)
	{
		
		Category category=this.modelMapper.map(categoryDto, Category.class);
//		Category category=new Category();
//			
//			category.setCategoryTitle(categoryDto.getCategoryTitle());
//			category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		return category;
	}
	
	public CategoryDto categoryToDto(Category category)
	{
		
		CategoryDto categoryDto=this.modelMapper.map(category, CategoryDto.class);
//		CategoryDto categoryDto=new CategoryDto();
//		
//		categoryDto.setCategoryTitle(category.getCategoryTitle());
//		categoryDto.setCategoryDescription(category.getCategoryDescription());
		
		return categoryDto;
	}

}
