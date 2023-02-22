package com.iktpreobuka.Project_example.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.Project_example.Service.OfferService;
import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.CategoryEntity;
import com.iktpreobuka.Project_example.entites.OfferEntity;
import com.iktpreobuka.Project_example.entites.OfferStatus;
import com.iktpreobuka.Project_example.entites.UserEntity;
import com.iktpreobuka.Project_example.entites.UserRole;
import com.iktpreobuka.Project_example.repositories.CategoryRepository;
import com.iktpreobuka.Project_example.repositories.OfferRepository;
import com.iktpreobuka.Project_example.repositories.UserRepository;

@RestController
@RequestMapping(path = "/project/offers")
public class OfferController {

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferService offerService;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<OfferEntity> getAll() {
		return offerRepository.findAll();

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@Valid @RequestBody OfferEntity offer, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
		}
		offerService.add(offer);
		return new ResponseEntity<>(offer, HttpStatus.OK);

		}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
		.collect(Collectors.joining(" "));
	}
	
	

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modify(@PathVariable Integer id, @Valid @RequestBody OfferEntity of, BindingResult result) {
		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
			}
			offerService.modify(id, of);
			return new ResponseEntity<>(of, HttpStatus.OK);

		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(offerService.delete(id));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> id(@PathVariable Integer id)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(offerService.id(id));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "changeOffer/{id}/status/{status}")
	public ResponseEntity<?> status(@PathVariable Integer id, @PathVariable OfferStatus status) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(offerService.status(id, status));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findByPrice/{lowerPrice}/and/{upperPrice}")
	public ResponseEntity<?> between(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(offerService.between(lowerPrice, upperPrice));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/{categoryId}/seller/{sellerId}")
	public ResponseEntity<?> addCategoryAndSeller(@PathVariable Integer categoryId, @PathVariable Integer sellerId,
			@Valid @RequestBody OfferEntity offer,BindingResult result) {

		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
				}
			offerService.addCategoryAndSeller(categoryId, sellerId, offer);
			return new ResponseEntity<>(offer, HttpStatus.OK);
		}
		 catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/category/{categoryId}")
	public ResponseEntity<?> modifyKateggory(@PathVariable Integer id, @PathVariable Integer categoryId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(offerService.modifyKateggory(id,categoryId));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}
}
