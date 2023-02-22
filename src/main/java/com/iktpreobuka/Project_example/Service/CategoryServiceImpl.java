package com.iktpreobuka.Project_example.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.CategoryEntity;
import com.iktpreobuka.Project_example.repositories.CategoryRepository;

@Service

public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public CategoryEntity addNewCategory(CategoryEntity categor)  {
//		List<CategoryEntity> c=(List<CategoryEntity>) categoryRepository.findAll();
//		if (c.contains(categor)) {
//			throw new RESTError(1, "Category exists");
//		}
		return categoryRepository.save(categor);
	}

	@Override
	public Optional<CategoryEntity> getById(Integer id) throws RESTError  {
		if (!categoryRepository.existsById(id)) {
			throw new RESTError(1,"Category not exists");
		}
		return categoryRepository.findById(id);
	}

	@Override
	public CategoryEntity categoryDelete(Integer id) throws RESTError  {
		
		if(!categoryRepository.findById(id).isPresent()) {
			throw new RESTError(1,"Category not exists");
		}CategoryEntity category = categoryRepository.findById(id).get();
			categoryRepository.deleteById(id);
			return category;
//		for (CategoryEntity categ : categoryRepository.findAll()) {
//			if (!categ.getId().equals(id)) {
				
				
//				
//			}
//		}
//		
	}

	@Override
	public CategoryEntity modify(Integer id, String category_name, String category_description) throws RESTError  {
		
		for(CategoryEntity categ:categoryRepository.findAll()) {
			if(categ.getId().equals(id)) {
				categ.setCategory_name(category_name);
				categ.setCategory_description(category_description);
				return categ;
			}
		}
		throw new RESTError(1,"Category not exists");
	}
}
