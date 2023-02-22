package com.iktpreobuka.Project_example.Service;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.CategoryEntity;

public interface CategoryService {
	public CategoryEntity addNewCategory(CategoryEntity categor);
	
	public Optional<CategoryEntity> getById( Integer id) throws RESTError;
	
	public CategoryEntity categoryDelete( Integer id) throws RESTError;
	
	public CategoryEntity modify(Integer id,String category_name,String category_description) throws RESTError;
}
