package com.iktpreobuka.Project_example.controllers;

import java.text.ParseException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.Service.BillService;
import com.iktpreobuka.Project_example.Service.CategoryService;
import com.iktpreobuka.Project_example.Service.OfferService;
import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.CategoryEntity;
import com.iktpreobuka.Project_example.repositories.CategoryRepository;
import com.iktpreobuka.Project_example.security.Views;

@RestController
@RequestMapping(path = "/project/category")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	CategoryService categoryService;
	@Autowired
	OfferService offerService;
	@Autowired
	BillService billService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewCategory(@Valid @RequestBody CategoryEntity categor,BindingResult result)  {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
		}

		
			
			categoryService.addNewCategory(categor);
			return new ResponseEntity<>(categor, HttpStatus.OK);
		
			
			
		} 
		private String createErrorMessage(BindingResult result) {
			return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
			.collect(Collectors.joining(" "));
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<CategoryEntity> GetAll() {
		return categoryRepository.findAll();

	}
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET,value="/public")
	public Iterable<CategoryEntity> GetAllPublic() {
		return categoryRepository.findAll();

	}
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET,value="/private")
	public Iterable<CategoryEntity> GetAllPrivate() {
		return categoryRepository.findAll();

	}
	@JsonView(Views.Administator.class)
	@RequestMapping(method = RequestMethod.GET,value="/admin")
	public Iterable<CategoryEntity> GetAllAdmin() {
		return categoryRepository.findAll();

	}

	@RequestMapping(method = RequestMethod.GET, path = "/id")
	public ResponseEntity<?> getById(@RequestParam Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(categoryService.getById(id));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE,path = "/delete")
	public ResponseEntity<?> categoryDelete(@RequestParam Integer id) {
		
		try {
			
			if(offerService.offerForCategory(id) || billService.billForCategory(id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete offer and bill for this category ");
			}else {
				return ResponseEntity.status(HttpStatus.OK).body(categoryService.categoryDelete(id));
			}
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT,path = "/update")
	public ResponseEntity<?> modify(@RequestParam Integer id,@RequestParam String category_name,@RequestParam String category_description) {
	try {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.modify(id, category_name, category_description));
	}
	catch (RESTError e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}}

